import "../styles/header.component.css"

export default function Header(props) {
  return (
    <header>
      <div className="order-mapper" onClick={() => props.onOrderChangedListener("newest")}>
        <p className={props.sorting === "newest" ? "order-selected" : ""}>최신순</p>
      </div>
      <div className="order-mapper" onClick={() => props.onOrderChangedListener("popular")}>
        <p className={props.sorting === "popular" ? "order-selected" : ""}>인기순</p>
      </div>
    </header>
  )
}