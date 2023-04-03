import { Button, Card, CardActions, CardContent, IconButton, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";

export const PetAdd = () => {
	const navigate = useNavigate();

	const [pet, setPet] = useState<Pet>({
        id:0,
		name: "",
		petType:"",
        age:0,
        gender:"",
        price:0,
	});

	const addPet = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post(`/api/pets/add`, pet);
			navigate("/pets");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addPet}>
						<TextField
							id="name"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPet({ ...pet, name: event.target.value })}
						/>
						<TextField
							id="petType"
							label="Pet Type"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPet({ ...pet, petType: event.target.value })}
						/>

                        <TextField
							id="age"
							label="Age"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPet({ ...pet, age: parseInt(event.target.value) })}
						/>

                        <TextField
							id="gender"
							label="Gender"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPet({ ...pet, gender: event.target.value })}
						/>

                        <TextField
							id="price"
							label="Price"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setPet({ ...pet, price: parseInt(event.target.value) })}
						/>
						<Button type="submit">Add pet</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};