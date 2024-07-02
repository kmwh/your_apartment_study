
import React, { useState, useRef,useEffect } from "react";
import "./Post.css";
import chatIcon from "./assets/chat.svg"; // chat.svg 파일을 import
import heartIcon from "./assets/heart.svg"; // heart.svg 파일을 import
import fillHeartIcon from "./assets/fillHeart.svg"; // fillHeart.svg 파일을 import
import axios from "axios";
import './CommentList'
import CommentList from "./CommentList";
// import postData from "./JSON/post.json";

/**
 * 좋아요 수 댓글 수를 관리합니다.
 */
//+ 등록 URL
function Post({ sortURL, id, nickname, aptname, isLiked, likeCount, commentCount }) {   //postList 컴포넌트에서 json props를 받아온다.
  // isHeartClicked 상태 변수와 setIsHeartClicked 함수: 하트 아이콘 클릭 여부를 관리
  const [isHeartClicked, setIsHeartClicked] = useState(isLiked);
  // heart 상태 변수와 setHeart 함수: 하트 숫자를 관리
  const [heartNum, setHeartNum] = useState(likeCount);     //좋아요 수 개수
//처음 렌더링될 때 해당 게시글의 현재 좋아요 개수를 보여주기 위해 0에서 heart로 변경
  const [commentSelected, setCommentSelected] = useState(false);    //댓글 이모티콘이 선택 됐는지 아닌지

  const [inputValue, setInputValue] = useState(""); 
  const inputCommentRef = useRef(null);   //댓글 입력 창 관찰

  const [isBinClicked, setIsBinClicked]=useState(false);
  

  const [passwordInputValue,setPasswordInputValue]=useState("");  //비밀번호 입력 초기값
  const passwordInputRef = useRef(null); 
 

  useEffect(() => {
    fetch(`https://apt-api.blbt.app/v1/apartment/${id}/like`)
      .then((response) => response.json())
      .then((data) => {
      
        setHeartNum(data.likes);

        fetch( `${sortURL}`)

          .then((response) => response.json())
          .then((data) => {
            setIsHeartClicked(data.data.find((v) => v._id === id).isLiked);
          });
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }, [id]);

  const likePost = () => {
    const requestOptions = {
      method: "POST",
      redirect: "follow",
    };

    fetch(`https://apt-api.blbt.app/v1/apartment/${id}/like`, requestOptions)
      .then(() => {
        setHeartNum(heartNum + 1);
        setIsHeartClicked(true)

      })

  };

  const unlikePost = () => {
    const requestOptions = {
      method: "DELETE",
      redirect: "follow",
    };

    fetch(`https://apt-api.blbt.app/v1/apartment/${id}/like`, requestOptions)
      .then(() => {
        setHeartNum(heartNum - 1);
        setIsHeartClicked(false);


      })

  };


  const toggleHeart = () => {
    if (isHeartClicked) {
      unlikePost();
    } else {
      likePost();
    }
  };

  const toggleCommentSelected = () => {
    setCommentSelected(!commentSelected);
  };



  const onClickCommentAdd = () => {             //댓글 입력 
    const newComment = inputCommentRef.current.value;
    setInputValue(newComment);
    
    
    let data = JSON.stringify({
      "nickname": `익명${commentCount+1}`,
      "comment": `${newComment}`,
      "password": "P@ssw0rd"
    });
    
    let config = {
      method: 'post',
      maxBodyLength: Infinity,
      url: `https://apt-api.blbt.app/v1/apartment/${id}/comments`,
      headers: { 
        'Content-Type': 'application/json'
      },
      data : data
    };
    
    axios.request(config)
    .then((response) => {
      console.log(JSON.stringify(response.data));
    })
    .catch((error) => {
      console.log(error);
    });
    
    inputCommentRef.current.value = "";
    setInputValue("");
  };

  const onChangeInput = (e) => {    //댓글입력창
      setInputValue(e.target.value);
  };



  
  const onClickBin=()=>{        // 기존 post에서 쓰레기통 클릭-> 삭제칸, 삭제칸 아니요 클릭->기존 post
    if(isBinClicked==false){
      setIsBinClicked(true)
    }
    else{
      setIsBinClicked(false)
    }
  }

  const onChangePasswordInput = (e) => {      //비밀번호창 변화 관찰
    setPasswordInputValue(e.target.value);
  };
  const onClickDelete=()=>{         //삭제칸에서 '네'라고 답했을 시 일어나는 이벤트
    const newInput = passwordInputRef.current.value;
    setPasswordInputValue(newInput)
      let data = JSON.stringify({
        "password": `${passwordInputValue}`
      });
      
      let config = {
        method: 'delete',
        maxBodyLength: Infinity,
        url: `https://apt-api.blbt.app/v1/apartment/${id}`,
        headers: { 
          'Content-Type': 'application/json'
        },
        data : data
      };
      
      axios.request(config)
      .then((response) => {
        console.log(JSON.stringify(response.data));
        alert("삭제되었습니다!")
      })
      .catch((error) => {
        console.log(error);
        alert("비밀번호가 틀렸습니다!")
      });
      
   


    passwordInputRef.current.value=""
    setPasswordInputValue("");
  }





  return (
    <div id="post">
      {isBinClicked?(<div id="delete_div">
        <h3>삭제하시겠습니까?</h3>
        <div id="delete_form">
          <input ref={passwordInputRef} value={passwordInputValue} onChange={onChangePasswordInput} type="password" placeholder="비밀번호를 입력하세요" required></input>
          <button id="delete_yes" onClick={onClickDelete}>네</button>
          <button id="delete_no" onClick={onClickBin}>아니요</button>
        </div>
        
      </div>)
      :(
        <div id="post_contents">
        <h2 id="nickname">{nickname}</h2>
        <img id="bin" src="assets/del.svg" onClick={onClickBin}></img>
        <p id="aptname">{aptname}</p>
        <div id="heart_comment">
          <img
            src={isHeartClicked ? fillHeartIcon : heartIcon}
            onClick={toggleHeart}
            id="heart"
            alt="Heart Icon"
            className="icon"
          />
          <p id="countHeart">{heartNum}</p>
          <img
            src={chatIcon}
            onClick={toggleCommentSelected}
            id="comment"
            alt="Comment Icon"
            className="icon"
          />

          {/* 현재 댓글 숫자를 표시 */}
          <p id='countComment'>{commentCount}</p>
        </div>
        {commentSelected &&
          <div id="comment_div">
            <div id='input_comment_div'>
              <input ref={inputCommentRef} value={inputValue} onChange={onChangeInput} type='text' placeholder='댓글을 입력하세요.' id='input_comment'/>
              
              <button onClick={onClickCommentAdd} >댓글 추가</button>
            </div>
          
              <CommentList _id={id}  />
          
          </div>

        }

      </div>
      )}
      
    </div>
  );
}

export default Post;
