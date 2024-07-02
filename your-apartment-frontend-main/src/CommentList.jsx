import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Comment from './Comment';
import '../src/CommentList.css'

function CommentList({ _id }) {
  const [comments, setComments] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const config = {
          method: 'get',
          url: `https://apt-api.blbt.app/v1/apartment/${_id}/comments`,
          headers: {
            'Content-Type': 'application/json',
          },
        };

        const response = await axios.request(config);
        
        setComments(response.data.comments); // 올바르게 상태를 업데이트
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, [comments]);


  return (
    <div id="comment_list">
      {comments.length > 0 ? (
        comments.map((comment) => (
          <Comment
            key={comment._id}
            id={comment._id}
            nickname={comment.nickname}
            comment={comment.comment}
          />
        ))
      ) : (
        <></>
      )}
    </div>
  );
}

export default CommentList;