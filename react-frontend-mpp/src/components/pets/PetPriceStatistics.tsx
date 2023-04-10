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
} from "@mui/material";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { Pet } from "../../models/Pets";
import { Link, useNavigate, useParams } from "react-router-dom";
import { GlobalURL } from "../../main";


export const PetPriceStatistics = () => {
    const [loading, setLoading] = useState(false);
	const [pets, setPets] = useState([]);


    useEffect(() => {
        setLoading(true);
		fetch(GlobalURL+`/pets/price`)
        //fetch(`/api/pets/price`)
            .then((res) => res.json())
            .then((data) => {
                setPets(data),
                setLoading(false);
            });
    },[]);    



    return (
		<Container>
			<h1>All pets</h1>

			{loading && <CircularProgress />}
			{!loading && pets.length === 0 && <p>No pets found</p>}
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
                                <TableCell align="center">
                                     <b style={{fontWeight: 'bold'}}>Price</b>
                                 </TableCell>
                                 <TableCell align="center">Details</TableCell>
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
                                    <TableCell align="center">
                                         <b style={{fontWeight: 'bold'}}>{pet.price}</b>
                                    </TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/pets/${pet.id}/details`}>
											<Tooltip title="View pet details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
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


