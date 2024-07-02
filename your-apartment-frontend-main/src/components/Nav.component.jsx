import "../styles/nav.component.css"
import apartment from "../assets/apartment.svg" 
export default function NavComponent() {
  return (
    
      <nav id="navcomponent_div">
        <img src={apartment}/>
        <h2>너의 아파트는</h2>
        <h3>멋쟁이사자처럼</h3>
        <h3>야무진친구들</h3>

        <div className="developers">
          <h4>❤︎ 담아 만듭니다.</h4>
          <p>김수빈</p>
          <p>이노학</p>
          <p>최혜선</p>
          <p>김원호</p>
          <p>김재연</p>
          <p>이지혜</p>
        </div>
      </nav>

  );
}