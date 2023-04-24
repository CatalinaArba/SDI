import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../components";
import { AdoptionCustomer } from "../../models/AdoptionCustomer";

export const AdoptionCustomerDetails = () => {
	const { id} = useParams<{ id: string }>();
	const [adoptionCustomer, setAdoptionsCustomer] = useState<AdoptionCustomer>();
	useEffect(() => {
        const fetchAdoptionCustomer = async () => {
          try {
			const response = await fetch(GlobalURL+`/adoptionCustomer/${id}`);
            //const response = await fetch(`${BACKEND_API_URL}/pets/${id}/details`);
            const data = await response.json();
            setAdoptionsCustomer(data);
            
            
          } catch (error) {
            console.error(`Failed to fetch adoption-customer with ID ${id}:`, error);
          }
        };
        fetchAdoptionCustomer();
      }, [id]);
           
    


	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Adoption-Customer Details</h1>
					<p>Contract: {adoptionCustomer?.adoptionContract}</p>
					<p>Customer Feedback: {adoptionCustomer?.customerFeedback}</p>
                    {adoptionCustomer?.adoptionAdoptionCustomer &&adoptionCustomer.customerAdoptionCustomer &&(
					<><p>Adoption ID : {adoptionCustomer?.adoptionAdoptionCustomer.id}</p><p>Adoption Date : {adoptionCustomer?.adoptionAdoptionCustomer.adoptionDate}</p><p>Adoption Fee : {adoptionCustomer?.adoptionAdoptionCustomer.adoptionFee}</p><p>Adoption Status : {adoptionCustomer?.adoptionAdoptionCustomer.adoptionStatus}</p><p>Adoption Location : {adoptionCustomer?.adoptionAdoptionCustomer.adoptionLocation}</p><p>Adoption Notes:{adoptionCustomer?.adoptionAdoptionCustomer.adoptionNotes}</p><p>Customer First Name:{adoptionCustomer?.customerAdoptionCustomer.firstName}</p><p>Customer Last Name:{adoptionCustomer?.customerAdoptionCustomer.lastName}</p><p>Customer Address:{adoptionCustomer?.customerAdoptionCustomer.address}</p><p>Customer Mail:{adoptionCustomer?.customerAdoptionCustomer.mail}</p><p>Customer Phone:{adoptionCustomer?.customerAdoptionCustomer.phone}</p></>)}
				</CardContent>
				<CardActions>
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end' }}>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer/${id}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer/${id}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
                    </div>
				</CardActions>
                
			</Card>
		</Container>
	);
};