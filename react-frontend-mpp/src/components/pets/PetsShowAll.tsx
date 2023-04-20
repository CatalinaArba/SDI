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

export const PetsShowAll = () => {
	const [loading, setLoading] = useState(false);
	const [pets, setPets] = useState([]);
	const [currentPage, setCurrentPage]=useState(0)
    const [pageSize, setPageSize] = useState(10);
    const [totalPets, setTotalPets] =useState(0)
	const [start_index,setStartIndex]=useState(0)
;
	useEffect(() => {
		setLoading(true);
  
		const fetchRecLbl = () => {
		  //fetch(GlobalURL+`/pets/count`)
		  fetch(`${BACKEND_API_URL}/pets/count`)
		  //fetch(`http://16.16.143.73:80/pets/count`)
		  .then((response) => response.json())
		  .then((count) => {
			//fetch(GlobalURL+`/pets/page/${currentPage}/size/${pageSize}`)
			fetch(`${BACKEND_API_URL}/pets/page/${currentPage}/size/${pageSize}`)
			//fetch(`http://16.16.143.73:80/pets/page/${currentPage}/size/${pageSize}`)
			.then((response) => response.json())
			.then((data) => {
			  setTotalPets(count);
			  setPets(data);
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


	// useEffect(() => {
	// 	setLoading(true);
	// 	fetch(GlobalURL + `/pets`)
	// 		//fetch(`/api/pets`)
	// 		.then((res) => res.json())
	// 		.then((data) => {
	// 			setPets(data);
	// 			setLoading(false);
	// 		});
	// }, []);

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
			<h1>All pets</h1>

			{loading && <CircularProgress />}
			{!loading && (
				<div style={{ display: 'flex', alignItems: 'center' }}>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/add`}>
						<Tooltip title="Add a new pet" arrow>
							<AddIcon color="primary" />
						</Tooltip>
					</IconButton>
				</div>
			)}
			{!loading && totalPets === 0 && <p>No pets found</p>}
			{!loading && (<div>
				<Button sx={{ color: "pink" }} onClick={sortPets} >
					Sort pets after names
				</Button>
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
				Page {currentPage+1} of {Math.ceil(totalPets/pageSize)}
			   </Box>
			   </div>
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
								<TableCell align="center">Description</TableCell>
								<TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{pets.map((pet: Pet, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{start_index+ index + 1}
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
									<TableCell align="center">{pet.description}</TableCell>
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


// import { MouseEvent, useEffect, useState } from "react";
// import {
// 	TableContainer,
// 	Paper,
// 	Table,
// 	TableHead,
// 	TableRow,
// 	TableCell,
// 	TableBody,
// 	CircularProgress,
// 	Container,
// 	IconButton,
// 	Tooltip,
// 	Card,
// 	FormControl,
// 	Button,
// 	Alert,
// } from "@mui/material";
// import { Link } from "react-router-dom";
// import ReadMoreIcon from "@mui/icons-material/ReadMore";
// import EditIcon from "@mui/icons-material/Edit";
// import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
// import AddIcon from "@mui/icons-material/Add";
// import { Pet } from "../../models/Pets";
// import { Row, Col, Pagination, PaginationItem, PaginationLink } from "reactstrap";
// import { GlobalURL } from "../../main";

// export const PetsShowAll = () => {
// 	const [loading, setLoading] = useState(false);
// 	const [pets, setPets] = useState([]);
// 	const [currentPage, setCurrentPage] = useState(1);
// 	const [pageSize, setPageSize] = useState(10);
// 	const [totalPages, setTotalPages] = useState(1);

// 	useEffect(() => {
// 		loadPets(currentPage, pageSize);
// 	}, [currentPage, pageSize]);

// 	const loadPets = (page: number, size: number) => {
// 		setLoading(true);
// 		fetch(GlobalURL + `/pets/page/${page}/size/${size}`)
// 			.then((res) => res.json())
// 			.then((data) => {
// 				setPets(data.content);
// 				setTotalPages(data.totalPages);
// 				setLoading(false);
// 			});
// 	};

// 	const handlePageChange = (page: number) => {
// 		setCurrentPage(page);
// 	};

// 	const sortPets = () => {
// 		const sortedPets = [...pets].sort((a: Pet, b: Pet) => {
// 			if (a.name < b.name) {
// 				return -1;
// 			}
// 			if (a.name > b.name) {
// 				return 1;
// 			}
// 			return 0;
// 		})
// 		console.log(sortedPets);
// 		setPets(sortedPets);
// 	}


// 	return (
// 		<Container>
// 			<h1>All pets</h1>

// 			{loading && <CircularProgress />}
// 			{!loading && pets.length === 0 && <p>No pets found</p>}
// 			{!loading && (
// 				<div style={{ display: "flex", alignItems: "center" }}>
// 					<IconButton component={Link} sx={{ mr: 3 }} to={`/pets/add`}>
// 						<Tooltip title="Add a new pet" arrow>
// 							<AddIcon color="primary" />
// 						</Tooltip>
// 					</IconButton>
// 				</div>
// 			)}
// 			{!loading && (
// 				<Button sx={{ color: "pink" }} onClick={sortPets}>
// 					Sort pets after names
// 				</Button>
// 			)}
// 			{!loading && pets.length > 0 && (
// 				<>
// 					<TableContainer component={Paper}>
// 						<Table sx={{ minWidth: 650 }} aria-label="simple table">
// 							<TableHead>
// 								<TableRow>
// 									<TableCell>#</TableCell>
// 									<TableCell align="left">Name</TableCell>
// 									<TableCell align="right">Type</TableCell>
// 									<TableCell align="right">Age</TableCell>
// 									<TableCell align="center">Gender</TableCell>
// 									<TableCell align="center">Price</TableCell>
// 									<TableCell align="center">Options</TableCell>
// 								</TableRow>
// 							</TableHead>
// 							<TableBody>
// 								{pets.map((pet: Pet, index) => (
// 									<TableRow key={index}>
// 										<TableCell component="th" scope="row">
// 											{index + 1}
// 										</TableCell>
// 										<TableCell component="th" scope="row">
// 											<Link to={`/pets/${pet.id}/details`} title="View pet details">
// 												{pet.name}
// 											</Link>
// 										</TableCell>
// 										<TableCell align="right">{pet.petType}</TableCell>
// 										<TableCell align="right">{pet.age}</TableCell>
// 										<TableCell align="center">{pet.gender}</TableCell>
// 										<TableCell align="center">{pet.price}</TableCell>
// 										<TableCell align="right">
// 											<IconButton
// 												component={Link}
// 												sx={{ mr: 3 }}
// 												to={`/pets/${pet.id}/details`}
// 											>
// 												<Tooltip title="View pet details" arrow>
// 													<ReadMoreIcon color="primary" />
// 												</Tooltip>
// 											</IconButton>

// 											<IconButton
// 												component={Link}
// 												sx={{ mr: 3 }}
// 												to={`/pets/${pet.id}/edit`}
// 												title="Edit pet details"
// 											>
// 												<EditIcon />
// 											</IconButton>

// 											<IconButton
// 												component={Link}
// 												sx={{ mr: 3 }}
// 												to={`/pets/${pet.id}/delete`}
// 												title="Delete pet"
// 											>
// 												<DeleteForeverIcon sx={{ color: "red" }} />
// 											</IconButton>
// 										</TableCell>
// 									</TableRow>
// 								))}
// 							</TableBody>
// 						</Table>
// 					</TableContainer>
// 					<Row className="justify-content-center">
// 						<Col md="6">
// 							<Pagination aria-label="Page navigation example">
// 								<PaginationItem disabled={currentPage <= 0}>
// 									<PaginationLink
// 										onClick={(e) => handleClick(e, currentPage - 1)}
// 										previous
// 										href="#"
// 									/>
// 								</PaginationItem>

// 								{pageNumbers.map((page: number) => (
// 									<PaginationItem
// 										key={page}
// 										active={page === currentPage}
// 										onClick={(e) => handleClick(e, page)}
// 									>
// 										<PaginationLink href="#">
// 											{page + 1}
// 										</PaginationLink>
// 									</PaginationItem>
// 								))}

// 								<PaginationItem disabled={currentPage >= pagesCount - 1}>
// 									<PaginationLink
// 										onClick={(e) => handleClick(e, currentPage + 1)}
// 										next
// 										href="#"
// 									/>
// 								</PaginationItem>
// 							</Pagination>
// 						</Col>
// 					</Row>

// 				</>)}
// 		</Container>
// 	);
// };


