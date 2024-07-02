import "../styles/home.component.css"
import {useState} from "react";
import Header from "./Header.component.jsx";
import PostList from "../PostList.jsx";
import ApartmentForm from "./ApartmentForm.component.jsx";

export default function HomeComponent() {
  const [orderBy, setOrderBy] = useState("newest");
  const [page, setPage] = useState(0)

  const onOrderChangedListener = (order) => {
    setOrderBy(order)
  }

  return (
    <section id="section_div">
      <Header sorting={orderBy} onOrderChangedListener={onOrderChangedListener} />
      <ApartmentForm />
      <PostList orderBy={orderBy} page={page} />
    </section>
  )
}