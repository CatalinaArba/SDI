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

export const PetDetails = () => {
	const { id} = useParams<{ id: string }>();
	const [pet, setPet] = useState<Pet>();
	useEffect(() => {
        const fetchPet = async () => {
          try {
			//const response = await fetch(GlobalURL+`/pets/${id}/details`);
            const response = await fetch(`${BACKEND_API_URL}/pets/${id}/details`);
            const data = await response.json();
            setPet(data);
            console.log(`Pet name: ${data.name}`);
            
          } catch (error) {
            console.error(`Failed to fetch pet with ID ${id}:`, error);
          }
        };
        fetchPet();
      }, [id]);
           
    


	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Pet Details</h1>
					<p>Pet Name: {pet?.name}</p>
					<p>Pet Type: {pet?.petType}</p>
					<p>Pet Age : {pet?.age}</p>
                    <p>Pet Gender : {pet?.gender}</p>
                    <p>Pet Price : {pet?.price}</p>
					<p>Pet Description : {pet?.description}</p>
					<p>Adoption id:{pet?.adoption?.id}</p>
                    <p>Adoption date:{pet?.adoption?.adoptionDate}</p>
                    <p>Adoption fee:{pet?.adoption?.adoptionFee}</p>
                    <p>Adoption location:{pet?.adoption?.adoptionLocation}</p>
                    <p>Adoption status:{pet?.adoption?.adoptionStatus}</p>
                    <p>Adoption notes:{pet?.adoption?.adoptionNotes}</p>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/${id}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/${id}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};