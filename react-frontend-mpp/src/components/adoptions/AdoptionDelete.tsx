import { Container, Card, CardContent, IconButton, CardActions, Button } from "@mui/material";
import { Link, useNavigate, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";

export const AdoptionDelete = () => {
	const { id } = useParams();
	const navigate = useNavigate();

	const handleDelete = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		//await axios.delete(`/api/pets/${id}/delete`);
		// go to courses list
		await axios.delete(GlobalURL+`/adoptions/${id}`);
		navigate("/adoptions");
	};

	const handleCancel = (event: { preventDefault: () => void }) => {
		event.preventDefault();
		// go to courses list
		navigate("/adoptions");
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					Are you sure you want to delete this adoption? This cannot be undone!
				</CardContent>
				<CardActions>
					<Button onClick={handleDelete}>Delete it</Button>
					<Button onClick={handleCancel}>Cancel</Button>
				</CardActions>
			</Card>
		</Container>
	);
};
