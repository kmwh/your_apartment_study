import "../styles/comment-write.component.css"
import {Button, Input, Space} from 'antd';
import {generateUserName} from "../utils.js";
import {useState} from "react";
import axios, {HttpStatusCode} from "axios";

export default function CommentWrite({ _id, onCommentAddedListener }) {
  const [commentForm, setCommentForm] = useState({
    nickname: generateUserName(),
    comment: "",
    password: generateUserName(), // 누군가가 고쳐주길 기원함
  })

  const onAddCommentClickListener = () => {
    if (commentForm.comment === "" || commentForm.nickname === "") {
      alert("닉네임 또는 댓글이 비어있습니다.")
      return;
    }

    let data = JSON.stringify(commentForm);

    let config = {
      method: 'post',
      maxBodyLength: Infinity,
      url: `https://apt-api.blbt.app/v1/apartment/${_id}/comments`,
      headers: {
        'Content-Type': 'application/json'
      },
      data : data
    };

    axios.request(config)
      .then((response) => {
        if (response.status === HttpStatusCode.Created) {
          onCommentAddedListener()
          setCommentForm({
            nickname: commentForm.nickname,
            comment: ""
          })
        }
      })
      .catch(() => {
        alert("오류가 발생했습니다.")
      });
  }

  return (
    <div className="comment-write-wrapper">
      <p>새 댓글 쓰기</p>
      <Space.Compact style={{width: "100%" ,height:'25px'}}>
        <Input style={{width: '20%' }} placeholder="닉네임" value={commentForm.nickname} onChange={(e) => setCommentForm({ ...commentForm, nickname: e.target.value })}/>
        <Input style={{width: '80%' }} placeholder="댓글을 입력하세요" value={commentForm.comment} onChange={(e) => setCommentForm({ ...commentForm, comment: e.target.value })}/>
      </Space.Compact>
      <Button  type="default" id="add-comment" onClick={onAddCommentClickListener}>등록</Button>
    </div>
  )
}