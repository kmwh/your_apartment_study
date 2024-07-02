import "../styles/post-item.component.css"
import heart from "../assets/heart.svg"
import fillHeart from "../assets/fillHeart.svg"
import chat from "../assets/chat.svg"
import del from "../assets/del.svg"
import axios, {HttpStatusCode} from "axios";
import {useState} from "react";
import {Modal, Input} from "antd";
import Comment from "./Comment.component.jsx";
import CommentWrite from "./CommentWrite.component.jsx";

  export default function PostItem({ _id, nickname, apartmentName, likeCount, isLiked, commentCount, gptScore, createdAt }) {
    const [newLikeCount, setNewLikeCount] = useState(likeCount)
    const [newIsLiked, setNewIsLiked] = useState(isLiked)
    const [comments, setComments] = useState([])
    const [isCommentClicked, setIsCommentClicked] = useState(false)

    const onLikeClickListener = () => {
      const options = {
        method: newIsLiked ? "DELETE" : "POST",
        url: `https://apt-api.blbt.app/v1/apartment/${_id}/like`,
      };

      axios.request(options).then()
      setNewIsLiked(!newIsLiked)
      setNewLikeCount(newIsLiked ? newLikeCount - 1 : newLikeCount + 1)
    }

    const onPostClickedListener = async () => {
      if(!isCommentClicked){        //댓글창을 여는 경우
        setIsCommentClicked(true)
        const response = await axios.get(`https://apt-api.blbt.app/v1/apartment/${_id}/comments`)
        if (response.status === HttpStatusCode.Ok) {
          setComments(response.data.comments)
        }
      }
      else{
        setComments([])
        setIsCommentClicked(false)
      }
      
   
      
    }

    const onCommentAddedListener = async () => {
      const response = await axios.get(`https://apt-api.blbt.app/v1/apartment/${_id}/comments`)
      if (response.status === HttpStatusCode.Ok) {
        setComments(response.data.comments)
      }
    }

    const showDeleteModal = () => {
      let password = '';

      Modal.confirm({
        title: '삭제 확인',
        content: (
          <Input.Password
            placeholder="비밀번호를 입력하세요"
            onChange={(e) => password = e.target.value}
          />
        ),
        onOk: () => {
          // You can make an API call to delete the post here
          axios.delete(`https://apt-api.blbt.app/v1/apartment/${_id}`, {
            data: {password}
          }).then(response => {
            if (response.status === HttpStatusCode.Ok) {
              window.location.href = "/"
            }
          }).catch(() => {
            alert("비밀번호가 잘못되었습니다.")
          })
        },
      });
    }

    return (
      <div className="post-item-container">
        <div className="post-header">
          <p>{nickname}</p>
          <p>&nbsp;·&nbsp;</p>
          <p>{createdAt}</p>
        </div>
        <p id="apartment-name" onClick={onPostClickedListener}>&quot;{apartmentName}&quot;</p>
        <p id="gpt-score">평가: <span>{gptScore}점</span></p>
        <div className="like-comment-delete-wrapper">
          {
            newIsLiked ? (
              <div id="like-icon-wrapper" className="icon-text-wrapper" >
                <img src={fillHeart} alt="좋아요 취소" onClick={onLikeClickListener}/>
                <p>{newLikeCount}</p>
              </div>
            ) : (
              <div id="like-icon-wrapper" className="icon-text-wrapper" onClick={onLikeClickListener}>
                <img src={heart} alt="좋아요 표시" onClick={onLikeClickListener}/>
                <p>{newLikeCount}</p>
              </div>
            )
          }
          <div id="text-icon-wrapper" className="icon-text-wrapper" >
            <img src={chat} alt="댓글" onClick={onPostClickedListener}/>
            <p>{commentCount}</p>
          </div>
          {/* <div style={{marginRight:'100px',flex: 2}}/> */}
          <div id="delete-icon-wrapper" className="icon-text-wrapper" onClick={showDeleteModal}>
            <img src={del} alt="삭제"/>
            <p>삭제</p>
          </div>
        </div>
        <div className="post-comments">
          <div className="post-comments-list">
          {
            comments.map((comment) => <Comment
              key={comment._id}
              nickname={comment.nickname}
              comment={comment.comment}
              createdAt={comment.createdAt} />)
          }
          </div>
          
          {
            isCommentClicked ?  <CommentWrite _id={_id} onCommentAddedListener={onCommentAddedListener} /> : <></>
          }
        </div>
      </div>
    )
}