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

export const PetUpdate = () => {
    const { id } = useParams();
	const navigate = useNavigate();
    const [loading, setLoading] = useState(true)
	const [pet, setPet] = useState<Pet>();
	useEffect(() => {
        const fetchPetDetails = async () => {
            try {
                const response = await fetch(`/pets/${id}/details`);
                const petDetails = await response.json();
                setPet(petDetails);
                setLoading(false);
            } catch (error) {
                console.log(error);
            }
        };

        fetchPetDetails();
    }, [id]);

    useEffect(() => {
		const fetchPet = async () => {
			const response = await fetch(`../../api/pet/${id}/edit`);
			const fetchedPet = await response.json();
			setPet(fetchedPet);
			setLoading(false);
		};
		fetchPet();
	}, [id]);

	const updatePet = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.put(`/api/pets/${id}/edit`, pet);
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
					<form onSubmit={updatePet}>
						<TextField
							id="name"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							value={pet.name}
							onChange={(event) => setPet({ ...pet, name: event.target.value })}
						/>
						<TextField
							id="petType"
							label="Pet Type"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							value={pet.petType}
							onChange={(event) => setPet({ ...pet, petType: event.target.value })}
						/>

                        <TextField
							id="age"
							label="Age"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							value={pet.age}
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
						<Button type="submit">Update pet details</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};