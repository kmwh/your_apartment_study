import "../styles/apartment-form.component.css"
import {useState} from "react";
import DataListComponent from "./DataList.component.jsx";
import axios from "axios";
import {generateUserName} from "../utils.js";

export default function ApartmentForm() {
  const [searchedApartments, setSearchedApartments] = useState([]);
  const [createApartmentForm, setCreateApartmentForm] = useState({
    nickname: generateUserName(),
    apartmentName: "",
    password: "",
  })
  const [selectedApartment, setSelectedApartment] = useState()
  const [submitClicked, setSubmitClicked] = useState(false)

  const onApartmentNameChanged = async (e) => {
    setSelectedApartment(null)
    const apartmentName = e.target.value;
    setCreateApartmentForm({
      ...createApartmentForm,
      apartmentName,
    })

    const res = await axios.get(`https://apt-api.blbt.app/v1/apartment/name?query=${apartmentName}`)
    setSearchedApartments(res.data.data)
  }

  const onApartmentNameSelectedHandler = (apartment) => {
    setSelectedApartment(apartment)
    setSearchedApartments([])
  }

  const onSubmit = async () => {
    setSubmitClicked(true)
    if (createApartmentForm.nickname == "" || createApartmentForm.password == "") {
      alert("닉네임 또는 비밀번호가 비어있습니다.");
      setSubmitClicked(false)
      return;
    }

    if (!selectedApartment) {
      alert("아파트를 리스트에서 선택해주세요!");
      setSubmitClicked(false)
      return;
    }

    try {
      await axios.post("https://apt-api.blbt.app/v1/apartment", {
        "nickname": createApartmentForm.nickname,
        "apartmentName": selectedApartment._id,
        "password": createApartmentForm.password
      });
      window.location.reload()
    } catch (ex) {
      if (ex.response.status === 409) {
        alert("이미 등록된 아파트 입니다.")
      } else {
        alert("등록에 실패했습니다. :(")
      }
    }
    setSubmitClicked(false)
  }

  return (
    <div className="create-apartment-form">
      <input id="apt-name"
             type="text"
             value={selectedApartment ? selectedApartment.apartmentName : createApartmentForm.apartmentName}
             placeholder="당신이 살고 있는 쌈@@뽕한 아파트 이름은 무엇인가요?"
             onChange={onApartmentNameChanged} />
      {
        !selectedApartment && searchedApartments.map((apartment) => <DataListComponent
          key={apartment._id}
          apartment={apartment}
          id={apartment._id}
          address={apartment.address}
          region={apartment.regionName}
          onApartmentNameSelectedHandler={onApartmentNameSelectedHandler}
        />)
      }

      <div className="owner-form">
        <input value={createApartmentForm.nickname} className="owner-input" type="text" placeholder="닉네임" style={{borderRight: "1px solid #3e3e3e"}}
               onChange={(e) => setCreateApartmentForm({
                 ...createApartmentForm, nickname: e.target.value })}
        />
        <input value={createApartmentForm.password} className="owner-input" type="password" placeholder="비밀번호" style={{marginLeft: "0"}}
               onChange={(e) => setCreateApartmentForm({
                 ...createApartmentForm, password: e.target.value })}/>
      </div>
      <br />
      <button type="button" className="btn btn-primary submit" onClick={onSubmit} disabled={submitClicked}>등록하기</button>
    </div>
  );
}