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
	Card,
	FormControl,
	Button,
	Alert,
	Box,
} from "@mui/material";
import { Link } from "react-router-dom";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { Pet } from "../../models/Pets";
import { Row, Col, Pagination, PaginationItem, PaginationLink } from 'reactstrap';
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../components";
import { Adoption } from "../../models/Adoption";

export const AdoptionShowAll = () => {
    const path = location.pathname;
	const [loading, setLoading] = useState(false);
	const [adoptions, setAdoptions] = useState([]);
	const [currentPage, setCurrentPage]=useState(0)
    const [pageSize, setPageSize] = useState(10);
    const [totalAdoptions, setTotalAdoptions] =useState(0)
	const [start_index,setStartIndex]=useState(0)
;
	useEffect(() => {
		setLoading(true);
  
		const fetchRecLbl = () => {
		  //fetch(GlobalURL+`/adoptions/count`)
		  fetch(`${BACKEND_API_URL}/adoptions/count`)
		  //fetch(`http://16.16.143.73:80/pets/count`)
		  .then((response) => response.json())
		  .then((count) => {
			//fetch(GlobalURL+`/adoptions/page/${currentPage}/size/${pageSize}`)
			fetch(`${BACKEND_API_URL}/adoptions/page/${currentPage}/size/${pageSize}`)
			//fetch(`http://16.16.143.73:80/pets/page/${currentPage}/size/${pageSize}`)
			.then((response) => response.json())
			.then((data) => {
			  setTotalAdoptions(count);
			  setAdoptions(data);
			  setLoading(false);
			  setStartIndex(currentPage*pageSize);
			});
		  })
		  .catch((error) => {
			console.error(error);
			setLoading(false);
		  });
		};
		fetchRecLbl();
	  }, [currentPage, pageSize]);


	const sortAdoptions = () => {
		const sortedAdoptions = [...adoptions].sort((a: Adoption, b:Adoption) => {
			if (a.adoptionDate > b.adoptionDate) {
				return -1;
			}
			if (a.adoptionDate < b.adoptionDate) {
				return 1;
			}
			return 0;
		})
		console.log(sortedAdoptions);
		setAdoptions(sortedAdoptions);
	}

	
	const handlePreviousPage = () => {
		if(currentPage>0)
		{
		  setCurrentPage(currentPage-1);
		}
	  };
	
	  const handleNextPage = () => {
		setCurrentPage(currentPage+1);
	  };
	


	return (
		<Container>
			<h1>All adoptions</h1>

			{loading && <CircularProgress />}
			{!loading && (
				<div style={{ display: 'flex', alignItems: 'center' }}>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/add`}>
						<Tooltip title="Add a new adoption" arrow>
							<AddIcon color="primary" />
						</Tooltip>
					</IconButton>
				</div>
			)}
			{!loading && totalAdoptions === 0 && <p>No adoptions found</p>}
			{!loading && (<div>
                <div>
				<Button sx={{ color: "black" }} onClick={sortAdoptions} >
					Sort adoptions after date
				</Button>
                <Button
                        variant={path.startsWith("/adoptions/avgPetPrice") ? "outlined" : "text"}
                        to="/adoptions/avgPetPrice"
                        component={Link}
                        color="inherit"
                        sx={{ mr: 5 }}
                        >
                        Average pet price statistics
                    </Button>
                </div>
				<Button
				sx={{color:"black"}}
				disabled={currentPage===0}
				onClick={handlePreviousPage}>
				  Previous Page
			  </Button>
			  <Button
			   sx={{color:"black"}} onClick={handleNextPage}>
				Next Page
			   </Button>

			   <Box mx={2} display="flex" alignItems="center">
				Page {currentPage+1} of {Math.ceil(totalAdoptions/pageSize)}
			   </Box>
			   </div>
			)}
			{!loading && adoptions.length > 0 && (
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="left">Adoption Date</TableCell>
								<TableCell align="right">Fee</TableCell>
								<TableCell align="right">Status</TableCell>
								<TableCell align="center">Location</TableCell>
								<TableCell align="center">Notes</TableCell>
								<TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{adoptions.map((adoption: Adoption, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{start_index+ index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/adoptions/${adoption.id}/details`} title="View adoption's details">
											{adoption.adoptionDate}
										</Link>
									</TableCell>
									<TableCell align="right">{adoption.adoptionFee}</TableCell>
									<TableCell align="right">{adoption.adoptionStatus}</TableCell>
									<TableCell align="center">{adoption.adoptionLocation}</TableCell>
									<TableCell align="center">{adoption.adoptionNotes}</TableCell>
									<TableCell align="right">
                                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end' }}>
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/adoptions/${adoption.id}/details`}>
											<Tooltip title="View adoption details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/${adoption.id}/edit`} title="Edit adoption details">
											<EditIcon />
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/${adoption.id}/delete`} title="Delete adoption">
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

		
		<Button
          sx={{color:"black"}}
          disabled={currentPage===0}
          onClick={handlePreviousPage}>
            Previous Page
          </Button>

        <Button sx={{color:"black"}} onClick={handleNextPage}>
          Next Page
        </Button>

		</Container >

		

	);
	

}