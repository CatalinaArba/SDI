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
import { AdoptionCustomer } from "../../models/AdoptionCustomer";
import { debounce } from "lodash";
import { Adoption } from "../../models/Adoption";
import { Customer } from "../../models/Customer";
import { BACKEND_API_URL } from "../../constants";

export const AdoptionCustomerUpdate = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [adoptionCustomer, setAdoptionsCustomer] = useState<AdoptionCustomer>({
    id: 0,
    adoptionContract: "",
    customerFeedback: "",
    idAdoptionAdoptionCustomer: 0,
    idCustomerAdoptionCustomer: 0,
  });

  useEffect(() => {
    const fetchPet = async () => {
      //const response = await fetch(GlobalURL + `/adoptionCustomer/${id}`);
      const response = await fetch( `${BACKEND_API_URL}/adoptionCustomer/${id}`);

      const fetchedPet = await response.json();
      setAdoptionsCustomer(fetchedPet);
      setLoading(false);
    };

    fetchPet();
  }, [id]);

  const updateAdoptionCustomer = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      //await axios.put(GlobalURL + `/adoptionCustomer/${id}`, adoptionCustomer);
      await axios.put(`${BACKEND_API_URL}/adoptionCustomer/${id}`, adoptionCustomer);
      navigate("/adoptionCustomer");
    } catch (error) {
      console.log(error);
    }
  };

  const [adoptions, setAdoptions] = useState<Adoption[]>([]);
  const [customers, setCustomers] = useState<Customer[]>([]);

  const fetchCustomersSuggestions = async (query: string) => {
    try {
      let url = GlobalURL + `/customers/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setCustomers(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };
  const fetchAdoptionSuggestions = async (query: string) => {
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

  const debouncedFetchCustomersSuggestions = useCallback(
    debounce(fetchCustomersSuggestions, 500),
    []
  );
  const debouncedFetchAdoptionsSuggestions = useCallback(
    debounce(fetchAdoptionSuggestions, 500),
    []
  );

  const handleAdoptionsInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchAdoptionsSuggestions(value);
    }
  };
  const handleCustomersInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchCustomersSuggestions(value);
    }
  };

  useEffect(() => {
    return () => {
      debouncedFetchAdoptionsSuggestions.cancel();
      debouncedFetchCustomersSuggestions.cancel();
    };
  }, [debouncedFetchAdoptionsSuggestions, debouncedFetchCustomersSuggestions]);

  return (
    <Container>
      <Card>
        {!loading && (
          <div>
            <CardContent>
              <IconButton component={Link} sx={{ mr: 3 }} to={`/adoptionCustomer`}>
                <ArrowBackIcon />
              </IconButton>{" "}
              <form onSubmit={updateAdoptionCustomer}>
                <TextField
                  id="contract"
                  label="Contract"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  onChange={(event) =>
                    setAdoptionsCustomer({
                      ...adoptionCustomer,
                      adoptionContract: event.target.value,
                    })
                  }
                  value={adoptionCustomer.adoptionContract}
                />
                <TextField
                  id="customer Feedback"
                  label="Caustomer Feedback"
                  variant="outlined"
                  fullWidth
                  sx={{ mb: 2 }}
                  value={adoptionCustomer.customerFeedback}
                  onChange={(event) =>
                    setAdoptionsCustomer({
                      ...adoptionCustomer,
                      customerFeedback: event.target.value,
                    })
                  }
                />
                <Autocomplete
                  id="adoption"
                  options={adoptions}
                  getOptionLabel={(option) =>
                    `${option.id},  ${option.adoptionDate}, ${option.adoptionLocation}, ${option.adoptionStatus}, ${option.adoptionNotes}`
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Adoption ID"
                      variant="outlined"
                    />
                  )}
                  filterOptions={(options, date) =>
                    options.filter((option) =>
                      option.id.toString().startsWith(date.inputValue)
                    )
                  }
                  onInputChange={handleAdoptionsInputChange}
                  onChange={(event, value) => {
                    if (value) {
                      setAdoptionsCustomer({
                        ...adoptionCustomer,
                        idAdoptionAdoptionCustomer: value.id,
                      });
                    }
                  }}
                />
                <div>
                  <br></br>
                </div>
                <Autocomplete
                  id="customer"
                  options={customers}
                  getOptionLabel={(option) =>
                    `${option.firstName}  ${option.lastName}`
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Customer first name"
                      variant="outlined"
                    />
                  )}
                  filterOptions={(options, state) =>
                    options.filter((option) =>
                      option.firstName
                        .toLocaleLowerCase()
                        .includes(state.inputValue.toLocaleLowerCase())
                    )
                  }
                  onInputChange={handleCustomersInputChange}
                  onChange={(event, value) => {
                    if (value) {
                      setAdoptionsCustomer({
                        ...adoptionCustomer,
                        idCustomerAdoptionCustomer: value.id,
                      });
                    }
                  }}
                />

                <Button type="submit">Update adoption-customer details</Button>
              </form>
            </CardContent>
            <CardActions></CardActions>
          </div>
        )}
      </Card>
    </Container>
  );
};
