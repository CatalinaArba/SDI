import { Container, Card, CardContent, IconButton, CardActions, Button } from "@mui/material";
import { Link, useNavigate, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../components";

export const AdoptionCustomerDelete = () => {
	const { id } = useParams();
	const navigate = useNavigate();

	const handleDelete = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		//await axios.delete(`/api/pets/${id}/delete`);
		// go to courses list
		//await axios.delete(GlobalURL+`/adoptionCustomer/${id}`);
        await axios.delete(`${BACKEND_API_URL}/adoptionCustomer/${id}`);

		navigate("/adoptionCustomer");
	};

	const handleCancel = (event: { preventDefault: () => void }) => {
		event.preventDefault();
		// go to courses list
		navigate("/adoptionCustomer");
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					Are you sure you want to delete this adoption-customer? This cannot be undone!
				</CardContent>
				<CardActions>
					<Button onClick={handleDelete}>Delete it</Button>
					<Button onClick={handleCancel}>Cancel</Button>
				</CardActions>
			</Card>
		</Container>
	);
};
