import {
  Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";
import { debounce } from "lodash";
import { Adoption } from "../../models/Adoption";
import { AdoptionCustomer } from "../../models/AdoptionCustomer";
import { AdoptionDTOWithCustomerIds } from "../../models/AdoptionDTOWithCustomerIds";

export const AdoptionUpdate = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [adoption, setAdoption] = useState<Adoption>(
        {
          id: 0,
          adoptionDate: "",
          adoptionFee: 0,
          adoptionStatus: "",
          adoptionLocation: "",
          adoptionNotes: "",
          pet: [] as Pet[],
          adoptionCustomers: [] as AdoptionCustomer[],
      }
      );
  
  useEffect(() => {
    setLoading(true)
    const fetchAdoption = async () => {
      try {
        const response = await fetch(GlobalURL+`/adoptions/${id}`);
        //const response = await fetch(`${BACKEND_API_URL}/pets/${id}/details`);
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

  const updateAdoption = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.put(GlobalURL + `/adoptions/${id}`, adoption);
      navigate(`/adoptions`);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Container>
      <Card>
        {!loading &&adoption  && (
          <div>
            <CardContent>
              <IconButton component={Link} sx={{ mr: 3 }} to={`/adoptions`}>
                <ArrowBackIcon />
              </IconButton>{" "}
              <form onSubmit={updateAdoption}>
                <TextField
                 value={adoption.adoptionDate}
                  id="adoptionDate"
                  label="Adoption Date"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>{
                      setAdoption({
                        ...adoption,
                        adoptionDate: event.target.value,
                      });
                  }}
                  
                />
                <TextField
                  id="adoptionFee"
                  label="Adoption Fee"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  value={adoption.adoptionFee}
                  onChange={(event) =>
                    setAdoption({
                      ...adoption,
                      adoptionFee: parseInt(event.target.value),
                    })
                  }
                />

                <TextField
                  id="adoptionStatus"
                  label="Status"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  value={adoption.adoptionStatus}
                  onChange={(event) =>
                    setAdoption({
                      ...adoption,
                      adoptionStatus: event.target.value,
                    })
                  }
                />

                <TextField
                  id="adoptionLocation"
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
                  value={adoption.adoptionLocation}
                />

                <TextField
                  id="adoptionNotes"
                  label="Notes"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setAdoption({
                      ...adoption,
                      adoptionNotes: event.target.value,
                    })
                  }
                  value={adoption.adoptionNotes}
                />
                <Button type="submit">Update adoption details</Button>
              </form>
            </CardContent>
          </div>
        )}
      </Card>
    </Container>
  );
};
