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
import { AdoptionCustomer } from "../../models/AdoptionCustomer";

export const AdoptionCustomerShowAll = () => {
	const [loading, setLoading] = useState(false);
	const [adoptionsCustomer, setAdoptionsCustomer] = useState([]);
	const [currentPage, setCurrentPage]=useState(0)
    const [pageSize, setPageSize] = useState(10);
    const [totalAdoptionsCustomer, setTotalAdoptionsCustomer] =useState(0)
	const [start_index,setStartIndex]=useState(0)
;
	useEffect(() => {
		setLoading(true);
  
		const fetchRecLbl = () => {
		  fetch(GlobalURL+`/adoptionCustomer/count`)
		  //fetch(`${BACKEND_API_URL}/pets/count`)
		  //fetch(`http://16.16.143.73:80/pets/count`)
		  .then((response) => response.json())
		  .then((count) => {
			fetch(GlobalURL+`/adoptionCustomer/page/${currentPage}/size/${pageSize}`)
			//fetch(`${BACKEND_API_URL}/pets/page/${currentPage}/size/${pageSize}`)
			//fetch(`http://16.16.143.73:80/pets/page/${currentPage}/size/${pageSize}`)
			.then((response) => response.json())
			.then((data) => {
			  setTotalAdoptionsCustomer(count);
			  setAdoptionsCustomer(data);
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


	const sortAdoptionCustomer = () => {
		const sortedAdoptionCustomer = [...adoptionsCustomer].sort((a: AdoptionCustomer, b:AdoptionCustomer) => {
			if (a.id < b.id) {
				return -1;
			}
			if (a.id > b.id) {
				return 1;
			}
			return 0;
		})
		console.log(sortedAdoptionCustomer);
		setAdoptionsCustomer(sortedAdoptionCustomer);
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
			<h1>All adoption-customer</h1>

			{loading && <CircularProgress />}
			{!loading && (
				<div style={{ display: 'flex', alignItems: 'center' }}>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer/add`}>
						<Tooltip title="Add a new adoption customer" arrow>
							<AddIcon color="primary" />
						</Tooltip>
					</IconButton>
				</div>
			)}
			{!loading && totalAdoptionsCustomer === 0 && <p>No adoption customer found</p>}
			{!loading && (<div>
				{/* <Button sx={{ color: "black" }} onClick={sortAdoptionCustomer} >
					Sort adoptions after date
				</Button> */}
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
				Page {currentPage+1} of {Math.ceil(totalAdoptionsCustomer/pageSize)}
			   </Box>
			   </div>
			)}
			{!loading && totalAdoptionsCustomer > 0 && (
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="left">Adoption Customer Contract</TableCell>
								<TableCell align="center">Customer Feedback</TableCell>
								<TableCell align="center">Options</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{adoptionsCustomer.map((adoptionCustomer: AdoptionCustomer, index) => (
								<TableRow key={index}>
									<TableCell component="th" scope="row">
										{start_index+ index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
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