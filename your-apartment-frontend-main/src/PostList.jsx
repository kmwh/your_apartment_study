import React, {useState, useEffect} from 'react';
import axios from 'axios';
import PostItem from "./components/PostItem.component.jsx";
import './PostList.css';

function PostList({ orderBy, page }) {
  const [posts, setPosts] = useState([])
  const [isPostLoading, setIsPostLoading] = useState(false)

  const fetch = async () => {
    const res = await axios.get(`https://apt-api.blbt.app/v1/apartment?page=${page}&size=9999&order=${orderBy}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    setPosts(res.data.data)
  }

  useEffect(() => {
    setIsPostLoading(true)
    fetch().then(() => setIsPostLoading(false))
  }, [orderBy, page]);

  
  return (
    <div id='PostList_div'>
      <div id="posts-container">
        {
          posts.map((post) => <PostItem
            key={post._id}
            _id={post._id}
            nickname={post.nickname}
            apartmentName={post.apartmentName.apartmentName}
            likeCount={post.likeCount}
            isLiked={post.isLiked}
            commentCount={post.commentCount}
            gptScore={post.gptScore}
            createdAt={post.createdAt}/>)
        }
      </div>
    </div>
  );
}

export default PostList;
