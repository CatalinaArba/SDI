import { Card, CardActions, CardContent, IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Tooltip } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../components";
import { Adoption } from "../../models/Adoption";
import { AdoptionDTOWithCustomerIds } from "../../models/AdoptionDTOWithCustomerIds";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import { AdoptionCustomer } from "../../models/AdoptionCustomer";

export const AdoptionDetails = () => {
    const [loading, setLoading] = useState(false);
	const { id} = useParams<{ id: string }>();
	const [adoption, setAdoption] = useState<Adoption>();
	useEffect(() => {
        setLoading(true)
        const fetchAdoption = async () => {
          try {
			//const response = await fetch(GlobalURL+`/adoptions/${id}`);
            const response = await fetch(`${BACKEND_API_URL}/adoptions/${id}`);
            const data = await response.json();
            setAdoption(data);
            setLoading(false)
            console.log(data);
          } catch (error) {
            console.error(`Failed to fetch adoption with ID ${id}:`, error);
          }
        };
        fetchAdoption();
      }, [id]);
           
    


	return (
		<Container>
			{<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Adoption Details</h1>
                    <p>Adoption Id: {adoption?.id}</p>
					<p>Adoption Date: {adoption?.adoptionDate}</p>
					<p>Fee: {adoption?.adoptionFee}</p>
					<p>Status : {adoption?.adoptionStatus}</p>
                    <p>Location: {adoption?.adoptionLocation}</p>
                    <p>Notes: {adoption?.adoptionNotes}</p>
                    {/* <p>Customers: {String(adoption?.customerIds.join(', '))}</p> */}
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/${id}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/${id}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			<p>The adopted pets</p>
            {!loading &&!adoption?.pet&& <p> No pets </p>}
            {!loading&& adoption?.pet &&(
                
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
								<TableCell align="center">Description</TableCell>
								<TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
                            
							{adoption?.pet.map((pet: Pet, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{ index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/pets/${pet.id}/details`} title="View pet details">
											{pet.name}
										</Link>
									</TableCell>
									<TableCell align="right">{pet?.petType}</TableCell>
									<TableCell align="right">{pet?.age}</TableCell>
									<TableCell align="center">{pet?.gender}</TableCell>
									<TableCell align="center">{pet?.price}</TableCell>
									<TableCell align="center">{pet?.description}</TableCell>
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

            <div>
            <br />
            <br />
            </div>
            <p>Adoption-customer details</p>
            {!loading && !adoption?.adoptionCustomers && <p>No adoption customer</p>}
				
			{!loading && adoption?.adoptionCustomers  && (
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="center">Contract</TableCell>
								<TableCell align="center">Customer Feedback</TableCell>
								<TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{adoption.adoptionCustomers.map((adoptionCustomer: AdoptionCustomer, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{ index + 1}
									</TableCell>
									<TableCell component="th" scope="row" align="center">
										<Link to={`/adoptionCustomer/${adoptionCustomer.id}/details`} title="View adoption's details">
											{adoptionCustomer.adoptionContract}
										</Link>
									</TableCell>
							
									<TableCell align="center">{adoptionCustomer.customerFeedback}</TableCell>
									
									<TableCell align="right">
                                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end' }}>
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/adoptionCustomer/${adoptionCustomer.id}/details`}>
											<Tooltip title="View adoption customer details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer/${adoptionCustomer.id}/edit`} title="Edit adoption customer details">
											<EditIcon />
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer/${adoptionCustomer.id}/delete`} title="Delete adoption customer">
											<DeleteForeverIcon sx={{ color: "red" }} />
										</IconButton>
                                        </div>
									</TableCell>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>

			)}    
        </Card>}
		</Container>
	);
};