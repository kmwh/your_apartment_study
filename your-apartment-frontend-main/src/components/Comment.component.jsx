import "../styles/comment.component.css"

export default function Comment({ nickname, comment, createdAt }) {
  return (
    <div className="comment-wrapper">
      <div className="comment-header">
        <p>{nickname}</p>
        <p>&nbsp;·&nbsp;</p>
        <p>{createdAt}</p>
      </div>
      <p id="content">{comment}</p>
    </div>
  )
}