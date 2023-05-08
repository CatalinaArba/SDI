import { useCallback, useEffect, useState } from "react"
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
import EqualizerIcon from '@mui/icons-material/Equalizer';
import axios from "axios";

export const AdoptionShowAll = () => {
    const path = location.pathname;

	const [loading, setLoading] = useState(false);
  	const [adoptions, setAdoptions] = useState<Adoption[]>([]);
  	const [page, setPage] = useState(0);
  	const [pageSize, setPageSize] = useState(100); //Items per Page

	  const fetchAdoptions = useCallback(async (page: number, pageSize: number) => {
		try {
		  const dataResponse = await axios.get(
			`${BACKEND_API_URL}/adoptions/page/${page}/size/${pageSize}`
			//GlobalURL+`/adoptions/page/${page}/size/${pageSize}`
		  );
		  const data = dataResponse.data;
	
		  setAdoptions(data);
		  setLoading(false);
		} catch (error) {
		  console.error(error);
		  setLoading(false);
		}
	  }, []);
	
	  useEffect(() => {
		console.log(
		  "useEffect triggered with page:",
		  page,
		  "and pageSize:",
		  pageSize
		);
		setLoading(true);
		fetchAdoptions(page, pageSize);
	  }, [fetchAdoptions, page, pageSize]);
	
	  const [totalAdoptions, setTotalAdoptions] = useState(0);
	  useEffect(() => {
		axios
		  .get(`${BACKEND_API_URL}/adoptions/count`)
		  //.get(GlobalURL+`/adoptions/count`)
		  .then((response) => {
			setTotalAdoptions(response.data);
		  })
		  .catch((error) => console.log(error));
	  }, []);
	
	  const totalPages = Math.max(1, Math.ceil(totalAdoptions / pageSize));
	
	  const startIdx = page * pageSize;
	  const endIdx = Math.min(startIdx + pageSize, totalAdoptions);
	
	  const getPageNumbers = () => {
		const pageNumbers = [];
		let i;
		for (i = 0; i < totalPages; i++) {
		  if (
			i === 0 ||
			i === totalPages - 1 ||
			(i >= page - 5 && i <= page + 5) ||
			i < 5 ||
			i > totalPages - 6
		  ) {
			pageNumbers.push(i);
		  } else if (
			pageNumbers[pageNumbers.length - 1] !== "..." &&
			(i < page - 5 || i > page + 5)
		  ) {
			pageNumbers.push("...");
		  }
		}
		return pageNumbers;
	  };
	
	  const handlePageClick = (pageNumber: number) => {
		setPage(pageNumber);
	  };

// 	const [loading, setLoading] = useState(false);
// 	const [adoptions, setAdoptions] = useState([]);
// 	const [currentPage, setCurrentPage]=useState(0)
//     const [pageSize, setPageSize] = useState(10);
//     const [totalAdoptions, setTotalAdoptions] =useState(0)
// 	const [start_index,setStartIndex]=useState(0)
// ;
// 	useEffect(() => {
// 		setLoading(true);
  
// 		const fetchRecLbl = () => {
// 		  //fetch(GlobalURL+`/adoptions/count`)
// 		  fetch(`${BACKEND_API_URL}/adoptions/count`)
// 		  //fetch(`http://16.16.143.73:80/pets/count`)
// 		  .then((response) => response.json())
// 		  .then((count) => {
// 			//fetch(GlobalURL+`/adoptions/page/${currentPage}/size/${pageSize}`)
// 			fetch(`${BACKEND_API_URL}/adoptions/page/${currentPage}/size/${pageSize}`)
// 			//fetch(`http://16.16.143.73:80/pets/page/${currentPage}/size/${pageSize}`)
// 			.then((response) => response.json())
// 			.then((data) => {
// 			  setTotalAdoptions(count);
// 			  setAdoptions(data);
// 			  setLoading(false);
// 			  setStartIndex(currentPage*pageSize);
// 			});
// 		  })
// 		  .catch((error) => {
// 			console.error(error);
// 			setLoading(false);
// 		  });
// 		};
// 		fetchRecLbl();
// 	  }, [currentPage, pageSize]);


// 	const sortAdoptions = () => {
// 		const sortedAdoptions = [...adoptions].sort((a: Adoption, b:Adoption) => {
// 			if (a.adoptionDate > b.adoptionDate) {
// 				return -1;
// 			}
// 			if (a.adoptionDate < b.adoptionDate) {
// 				return 1;
// 			}
// 			return 0;
// 		})
// 		console.log(sortedAdoptions);
// 		setAdoptions(sortedAdoptions);
// 	}

	
// 	const handlePreviousPage = () => {
// 		if(currentPage>0)
// 		{
// 		  setCurrentPage(currentPage-1);
// 		}
// 	  };
	
// 	  const handleNextPage = () => {
// 		setCurrentPage(currentPage+1);
// 	  };
	


	return (
		<Container>
			<h1>All adoptions</h1>

			{loading && <CircularProgress />}
			{!loading && (<div>
				<div style={{ display: 'flex', alignItems: 'center' }}>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions/add`}>
						<Tooltip title="Add a new adoption" arrow>
							<AddIcon color="primary" />
						</Tooltip>
					</IconButton>
				</div>
				<div>
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
				</div>
			)}
			{!loading && totalAdoptions === 0 && <p>No adoptions found</p>}
			{/* { { !loading &&(<div>
                
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
			)} } */}

{!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          {/* <div>
            Showing {startIdx + 1}-{endIdx} of {totalPeople} people
          </div> */} 

			<div className="pagination">
						
						{getPageNumbers().map((pageNumber, index) => (
						<button
							key={index}
							className={`page-numbers ${
							pageNumber === page ? "active" : ""
							}`}
							// className={`btn me-2 ${
							//   pageNumber === page ? "btn-primary" : "btn-secondary"
							// }`}
							onClick={() => handlePageClick(Number(pageNumber))}
							disabled={pageNumber === "..." || pageNumber === page}
						>
							{pageNumber === "..."
							? "..."
							: typeof pageNumber === "number"
							? pageNumber + 1
							: ""}
						</button>
						))}
					
					</div>
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
										{page*pageSize+ index + 1}
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

		
		{/* <Button
          sx={{color:"black"}}
          disabled={currentPage===0}
          onClick={handlePreviousPage}>
            Previous Page
          </Button>

        <Button sx={{color:"black"}} onClick={handleNextPage}>
          Next Page
        </Button> */}

		</Container >

		

	);
	

}