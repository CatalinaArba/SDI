import {
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";
import { Adoption } from "../../models/Adoption";
import { BACKEND_API_URL } from "../../components";

export const AdoptionAdd = () => {
  const navigate = useNavigate();

  const [adoption, setAdoption] = useState<Adoption>({
    id: 0,
    adoptionDate: "",
    adoptionFee: 0,
    adoptionStatus: "",
    adoptionLocation: "",
    adoptionNotes: "",
    pet: [],
    adoptionCustomers: [],
  });

  

  const addAdoption = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      //await axios.post(`/api/pets/add`, pet);
      //await axios.post(GlobalURL + `/adoptions`, adoption);
      await axios.post( `${BACKEND_API_URL}/adoptions`, adoption);
      navigate("/adoptions");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/pets`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addAdoption}>
            <TextField
              id="adoption date"
              label="Adoption Date"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoption({ ...adoption, adoptionDate: event.target.value })
              }
            />
            <TextField
              id="adoption fee"
              label="Fee"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoption({
                  ...adoption,
                  adoptionFee: parseInt(event.target.value),
                })
              }
            />

            <TextField
              id="status"
              label="Status"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoption({ ...adoption, adoptionStatus: event.target.value })
              }
            />

            <TextField
              id="location"
              label="Location"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoption({
                  ...adoption,
                  adoptionLocation: event.target.value,
                })
              }
            />

            <TextField
              id="notes"
              label="Notes"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoption({ ...adoption, adoptionNotes: event.target.value })
              }
            />
            <Button type="submit">Add adoption</Button>
          </form>
        </CardContent>
        <CardActions></CardActions>
      </Card>
    </Container>
  );
};
