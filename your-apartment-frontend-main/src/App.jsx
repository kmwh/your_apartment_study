
import './App.css'
import NavComponent from "./components/Nav.component.jsx";
import HomeComponent from "./components/Home.component.jsx";

function App() {


  return (
    <>
      <div className="container">
        <div id="main_mapper">
          <NavComponent />
          <HomeComponent />
        </div>
      </div>
    </>
  );
}

export default App
