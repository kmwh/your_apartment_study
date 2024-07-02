
import React from "react";
import'../src/Comment.css'
import { useState } from "react";
import axios from "axios";


function Comment({key, id, nickname, comment}) {
    const [isBinClicked,setIsBinClicked]=useState(false);
    const onClickBin=()=>{
      let data = JSON.stringify({
        "password": "P@ssw0rd"
      });
      
      let config = {
        method: 'delete',
        maxBodyLength: Infinity,
        url: `https://apt-api.blbt.app/v1/apartment/comments/${id}`,
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
      
    }
  return (
    <div id="comment_content_div">
        <p id="comment_name">{nickname}</p>
        <p id="comment_content">{comment}</p>
        <img id="comment_bin" src="assets/del.svg" onClick={onClickBin}></img>
    </div>
  );
}

export default Comment
