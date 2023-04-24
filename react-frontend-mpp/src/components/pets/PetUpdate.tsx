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

export const PetUpdate = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [pet, setPet] = useState<Pet>({
    id: 0,
    name: "",
    petType: "",
    age: 0,
    gender: "",
    price: 0,
    description: "",
    adoptionId: 0,
  });

  useEffect(() => {
    const fetchPet = async () => {
      const response = await fetch(GlobalURL + `/pets/${id}/details`);
      const fetchedPet = await response.json();
      setPet(fetchedPet);
      setLoading(false);
    };

    fetchPet();
  }, [id]);

  const updatePet = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.put(GlobalURL + `/pets/${id}`, pet);
      navigate("/pets");
    } catch (error) {
      console.log(error);
    }
  };

  const [adoptions, setAdoptions] = useState<Adoption[]>([]);

  const fetchSuggestions = async (query: string) => {
    try {
      let url = GlobalURL + `/adoptions/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setAdoptions(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const debouncedFetchSuggestions = useCallback(
    debounce(fetchSuggestions, 500),
    []
  );

  const handleInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchSuggestions(value);
    }
  };

  useEffect(() => {
    return () => {
      debouncedFetchSuggestions.cancel();
    };
  }, [debouncedFetchSuggestions]);

  return (
    <Container>
      <Card>
        {!loading && (
          <div>
            <CardContent>
              <IconButton component={Link} sx={{ mr: 3 }} to={`/pets`}>
                <ArrowBackIcon />
              </IconButton>{" "}
              <form onSubmit={updatePet}>
                <TextField
                  id="name"
                  label="Name"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setPet({ ...pet, name: event.target.value })
                  }
                  value={pet.name}
                />
                <TextField
                  id="petType"
                  label="Pet Type"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  value={pet.petType}
                  onChange={(event) =>
                    setPet({ ...pet, petType: event.target.value })
                  }
                />

                <TextField
                  id="age"
                  label="Age"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  value={pet.age}
                  onChange={(event) =>
                    setPet({ ...pet, age: parseInt(event.target.value) })
                  }
                />

                <TextField
                  id="gender"
                  label="Gender"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setPet({ ...pet, gender: event.target.value })
                  }
                  value={pet.gender}
                />

                <TextField
                  id="price"
                  label="Price"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setPet({ ...pet, price: parseInt(event.target.value) })
                  }
                  value={pet.price}
                />
                <TextField
                  id="description"
                  label="Description"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setPet({ ...pet, description: event.target.value })
                  }
                  value={pet.description}
                />
				<div>
					<br>
					</br>
				</div>
                <Autocomplete
                  id="adoption"
                  options={adoptions}
                  getOptionLabel={(option) =>
                    `${option.id},  ${option.adoptionDate}, ${option.adoptionLocation}, ${option.adoptionStatus}, ${option.adoptionNotes}`
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Adoption"
                      variant="outlined"
                    />
                  )}
                  filterOptions={(options, date) =>
                    options.filter((option) =>
                      option.id.toString().startsWith(date.inputValue)
                    )
                  }
                  onInputChange={handleInputChange}
                  onChange={(event, value) => {
                    if (value) {
						setPet({ ...pet, adoptionId: value.id});
                    }
                  }}
                />
                <Button type="submit">Update pet details</Button>
              </form>
            </CardContent>
            <CardActions></CardActions>
          </div>
        )}
      </Card>
    </Container>
  );
};
