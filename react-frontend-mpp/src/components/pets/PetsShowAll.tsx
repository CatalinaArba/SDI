import { useEffect, useState } from "react"
import {
	TableContainer,
	Paper,
	Table,
	TableHead,
	TableRow,
	TableCell,
	TableBody,
	CircularProgress,
	Container,
	IconButton,
	Tooltip,
	Button,
} from "@mui/material";
import { Link } from "react-router-dom";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { Pet } from "../../models/Pets";


export const PetsShowAll = () => {
    const [loading, setLoading] = useState(false);
    const [pets, setPets] = useState([]);

    useEffect(() => {
        setLoading(true);
		fetch(`/api/pets`)
          .then((res) => res.json())
          .then((data) => {
            setPets(data);
            setLoading(false);
          });
      }, []);

	  const sortPets = () => {
        const sortedPets = [...pets].sort((a: Pet, b: Pet) => {
            if (a.name < b.name) {
                return -1;
            }
            if (a.name > b.name) {
                return 1;
            }
            return 0;
        })
        console.log(sortedPets);
        setPets(sortedPets);
    }


    return (
		<Container>
			<h1>All pets</h1>

			{loading && <CircularProgress />}
			{!loading && pets.length === 0 && <p>No pets found</p>}
			{!loading && (
				<div style={{display:'flex', alignItems:'center'}}>
                <IconButton component={Link} sx={{mr: 3 }} to={`/pets/add`}>
					<Tooltip title="Add a new pet" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
                </div>
			)}
			{!loading && (
                <Button sx={{color:"pink"}} onClick={sortPets} >
                    Sort pets after names
                </Button>
            )}
			{!loading && pets.length > 0 && (
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="left">Name</TableCell>
								<TableCell align="right">Type</TableCell>
								<TableCell align="right">Age</TableCell>
								<TableCell align="center">Gender</TableCell>
                                <TableCell align="center">Price</TableCell>
                                <TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{pets.map((pet:Pet, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/pets/${pet.id}/details`} title="View pet details">
											{pet.name}
										</Link>
									</TableCell>                                
                                    <TableCell align="right">{pet.petType}</TableCell>
								    <TableCell align="right">{pet.age}</TableCell>
								    <TableCell align="center">{pet.gender}</TableCell>
                                    <TableCell align="center">{pet.price}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/pets/${pet.id}/details`}>
											<Tooltip title="View pet details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/${pet.id}/edit`} title="Edit pet details">
											<EditIcon />
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/${pet.id}/delete`} title="Delete pet">
											<DeleteForeverIcon sx={{ color: "red" }} />
										</IconButton>
									</TableCell>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
			)}
		</Container>
	);
    
}


