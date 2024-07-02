import "../styles/data-list.component.css"

export default function DataListComponent({ apartment, onApartmentNameSelectedHandler }) {
  const { apartmentName, region, address } = apartment;
  return (
    <div className="datalist-wrapper" onClick={() => onApartmentNameSelectedHandler(apartment)}>
      <h4>{apartmentName}</h4>
      <div className="apartment-region-wrapper">
        <p>{region}</p>
        <p>{address}</p>
      </div>
    </div>
  );
}