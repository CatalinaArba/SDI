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
  TextField,
  Button,
  Box,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";
import { GlobalURL } from "../../main";
import axios from "axios";
import { BACKEND_API_URL } from "../../components"
import { Pet } from "../../models/Pets"

export const PetFilterPrice = () => {
  const { givenPrice } = useParams<{ givenPrice: string }>();
  const [[price], setPrice] = useState(givenPrice ?? "");
  const [loading, setLoading] = useState(true);
  const [pets, setPets] = useState([]);
  const [page, setPage] = useState(0);
  const [totalBusRoutes, setTotalBusRoutes] = useState(0);
  const [numItems, setNumItems] = useState(0);


  const fetchPets = useCallback(async () => {
    try {
      const url = `${BACKEND_API_URL}/pets/price/${price}/page/${page}/size/100`;
      //const url = GlobalURL+`/pets/price/${price}/page/${page}/size/100`;
      const response = await axios.get(url);
      const data = response.data;
      setPets(data);
      setNumItems(data.length);

      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  }, [price, page]);

  useEffect(() => {
    setLoading(true);
    fetchPets();
  }, [fetchPets]);

  const pageSize = 100;

  const handlePreviousPage = () => {
    // if (page > 0) {
    //   setPage(page - 1);
    // }

    setPage(Math.max(page - 1, 0));
  };

  const handleNextPage = () => {
    setPage(page + 1);
    //setPage(Math.min(page + 1, totalPages - 1));
  };

  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalBusRoutes);

  const canNextPage = numItems === pageSize; //&& endIdx < totalBusRoutes;


  const handlePriceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(event.target.value);
  };

  // const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
  //   event.preventDefault();
  //   setDistance(distance);
  // };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (price === "") {
      setPrice("");
      return;
    }
    if (price === givenPrice) {
      return;
    }
    setPage(0);
  };

  return (
    <Container>
      <h1>All Pets filtered: &gt; givenPrice </h1>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Price"
          value={price}
          onChange={handlePriceChange}
        />
      </form>

      {loading && <CircularProgress />}

      {!loading && pets.length == 0 && <div>No pets found. Introduce a value </div>}
      {/* {!loading && distance === " " && <div>No Distance entered</div>} */}


      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <Button
            sx={{ color: "black" }}
            disabled={page === 0}
            onClick={handlePreviousPage}
          >
            Previous Page
          </Button>
          {/* <Button sx={{ color: "black" }} onClick={handleNextPage}>
            Next Page
          </Button> */}
          <Button
            sx={{ color: "black" }}
            onClick={handleNextPage}
            disabled={!canNextPage}
          >
            Next Page
          </Button>
        </div>
      )}

      {!loading && pets.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 800 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="left">Name</TableCell>
					<TableCell align="right">Type</TableCell>
					<TableCell align="right">Age</TableCell>
				    <TableCell align="center">Gender</TableCell>
					<TableCell align="center">Price</TableCell>
					<TableCell align="center">Description</TableCell>
              
              </TableRow>
            </TableHead>
            <TableBody>
              {pets.map((pet: Pet, index) => (
                <TableRow key={pet.id}>
                  <TableCell component="th" scope="row">
                    {index + 1}
                  </TableCell>
                  <TableCell align="right">{pet.name}</TableCell>
                  <TableCell align="right">{pet.petType}</TableCell>
					<TableCell align="right">{pet.age}</TableCell>
					<TableCell align="center">{pet.gender}</TableCell>
					<TableCell align="center">{pet.price}</TableCell>
					<TableCell align="center">{pet.description}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Container>
  );
};
