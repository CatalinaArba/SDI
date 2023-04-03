import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import SchoolIcon from "@mui/icons-material/School";
import LocalLibraryIcon from "@mui/icons-material/LocalLibrary";
import AccessAlarmIcon from '@mui/icons-material/AccessAlarm';
import ThreeDRotation from '@mui/icons-material/ThreeDRotation'
import PetsIcon from '@mui/icons-material/Pets';
import MenuIcon from '@mui/icons-material/Menu';
export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="fixed" sx={{ backgroundColor: "#ff69b4",marginBottom: "20px" }}>
				<Toolbar>
					<IconButton
						component={Link}
						to="/"
						size="large"
						edge="start"
						color="inherit"
						aria-label="menu"
						sx={{ mr: 2 }}>
						 <MenuIcon />
					</IconButton>
					<Typography variant="h6" component="div" sx={{ mr: 5 }}>
						Adoptions center
					</Typography>
					<Button
                        variant={path.startsWith("/pets") ? "outlined" : "text"}
                        to="/pets"
                        component={Link}
                        color="inherit"
                        sx={{ mr: 5 }}
                        startIcon={<PetsIcon />}
                        >
                        Pets
                    </Button>
                    <Button
						variant={path.startsWith("/pets/price") ? "outlined" : "text"}
						to="/pets/price"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<PetsIcon />}
                        >
						Prices list
					</Button>
				</Toolbar>
			</AppBar>
		</Box>
	);
};