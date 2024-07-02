package com.yamuzinfriends.yourapartment.services;

import com.yamuzinfriends.yourapartment.dtos.*;
import com.yamuzinfriends.yourapartment.models.Apartment;
import com.yamuzinfriends.yourapartment.models.ApartmentName;
import com.yamuzinfriends.yourapartment.models.Comment;
import com.yamuzinfriends.yourapartment.models.Like;
import com.yamuzinfriends.yourapartment.repositories.ApartmentNameRepository;
import com.yamuzinfriends.yourapartment.repositories.ApartmentRepository;
import com.yamuzinfriends.yourapartment.repositories.CommentRepository;
import com.yamuzinfriends.yourapartment.repositories.LikeRepository;
import com.yamuzinfriends.yourapartment.utils.components.GeneratePopularCursor;
import com.yamuzinfriends.yourapartment.utils.components.GetIpAddress;
import com.yamuzinfriends.yourapartment.utils.enums.GetApartmentsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {
  private final ApartmentRepository apartmentRepository;
  private final ApartmentNameRepository apartmentNameRepository;
  private final PasswordEncoder passwordEncoder;
  private final CommentRepository commentRepository;
  private final LikeRepository likeRepository;
  private final GetIpAddress getIpAddress;
  private final GPTScoringService gptScoringService;

  public ApartmentService(
      ApartmentRepository repository,
      PasswordEncoder passwordEncoder,
      CommentRepository commentRepository,
      LikeRepository likeRepository,
      GetIpAddress getIpAddress,
      ApartmentNameRepository apartmentNameRepository,
      GPTScoringService gptScoringService
  ) {
    this.apartmentRepository = repository;
    this.passwordEncoder = passwordEncoder;
    this.commentRepository = commentRepository;
    this.likeRepository = likeRepository;
    this.getIpAddress = getIpAddress;
    this.gptScoringService = gptScoringService;
    this.apartmentNameRepository = apartmentNameRepository;
  }

  public List<Apartment> getApartments(GetApartmentsRequestDto dto) {
    return this.apartmentRepository.findAll(
        dto.getOrder() == GetApartmentsOrder.NEWEST ?
            Sort.by(Sort.Direction.DESC, "_id") :
            Sort.by(Sort.Direction.DESC, "popularCursor")
    );
  }

  public Apartment getApartmentByName(String name) {
    ApartmentName apartmentName = this.apartmentNameRepository.findById(name)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return this.apartmentRepository.findByApartmentName(apartmentName);
  }

  public int getApartmentCount() {
    return (int) this.apartmentRepository.count();
  }

  public boolean isLikeClicked(String ipAddress, Apartment apartment) {
    return this.likeRepository.findFirstByApartmentAndIpAddress(apartment, ipAddress).isPresent();
  }

  public CreateApartmentResponseDto createApartment(CreateApartmentRequestDto dto) {
    ApartmentName name = this.apartmentNameRepository.findById(dto.getApartmentName())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    Apartment apartment = new Apartment();
    apartment.nickname = dto.getNickname();
    apartment.ipAddress = getIpAddress.getIpAddress();
    apartment.apartmentName = name;
    apartment.password = passwordEncoder.encode(dto.getPassword());
    apartment.createdAt = new Date();

    try {
      apartment.gptScore = this.gptScoringService.requestScore(name.apartmentName);
      apartment.popularCursor = GeneratePopularCursor.generatePopularCursor(
          apartment.gptScore,
          0,
          apartment.createdAt
      );
    } catch(IOException ex) {
      ex.printStackTrace();
    }

    apartmentRepository.save(apartment);
    return new CreateApartmentResponseDto(
        apartment._id,
        apartment.nickname,
        apartment.ipAddress,
        name.apartmentName,
        apartment.gptScore,
        apartment.createdAt
    );
  }

  public void deleteApartment(int id, String password) {
    Apartment apartment = apartmentRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + " not found"));
    if (!passwordEncoder.matches(password, apartment.password)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, id + " password does not match");
    }
    else {
      apartmentRepository.delete(apartment);
    }
  }

  public GetCommentsResponseDto getComments(int apartmentId) {
    Apartment apartment = apartmentRepository
            .findById(apartmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, apartmentId + " not found"));
    return new GetCommentsResponseDto(apartment.comments);
  }

  public CreateCommentResponseDto createComment(int apartmentId, CreateCommentRequestDto dto) {
    Apartment apartment = apartmentRepository
            .findById(apartmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, apartmentId + " not found"));

    Comment comment = new Comment();
    comment.apartment = apartment;
    comment.nickname = dto.getNickname();
    comment.comment = dto.getComment();
    comment.password = passwordEncoder.encode(dto.getPassword());
    comment.createdAt = new Date();

    commentRepository.save(comment);
    return new CreateCommentResponseDto(
        comment._id,
        comment.nickname,
        comment.comment,
        comment.createdAt
    );
  }

  public void deleteComment(DeleteCommentRequestDto dto) {
    Comment comment = commentRepository
            .findById(dto.getCommentId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

    if (passwordEncoder.matches(dto.getPassword(), comment.password)) {
      commentRepository.delete(comment);
    } else {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password does not match");
    }

  }

  public GetLikesResponseDto getLikes(int apartmentId) {
    int likeCount = apartmentRepository.countLikesByApartmentId(apartmentId);
    return new GetLikesResponseDto(likeCount);
  }

  public void createLike(int apartmentId) {
    Apartment apartment = apartmentRepository
            .findById(apartmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, apartmentId + " not found"));
    String ipAddress = getIpAddress.getIpAddress();
    Optional<Like> likeOpt = likeRepository.findByApartmentAndIpAddress(apartment, ipAddress);
    if (likeOpt.isEmpty()) {
      Like like = new Like();
      like.apartment = apartment;
      like.ipAddress = ipAddress;
      likeRepository.save(like);

      apartment.popularCursor = GeneratePopularCursor.generatePopularCursor(
          apartment.gptScore,
          apartment.likes.size() + 1,
          apartment.createdAt
      );
      this.apartmentRepository.save(apartment);
    }
    else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already liked by this user.");
    }
  }

  public void deleteLike(int apartmentId) {
    Apartment apartment = apartmentRepository
            .findById(apartmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, apartmentId + " not found"));
    String ipAddress = getIpAddress.getIpAddress();
    Optional<Like> likeOpt = likeRepository.findByApartmentAndIpAddress(apartment, ipAddress);
    if (likeOpt.isPresent()) {
      Like like = likeOpt.get();
      likeRepository.delete(like);
    }
    else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not liked by this user.");
    }
  }
}