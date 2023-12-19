import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Delete, Edit } from "@mui/icons-material";

function PassengerDetails({ name, dob, gender, email, phone_no, noGutter }) {
  return (
    <Box
      component="li"
      display="flex"
      justifyContent="space-between"
      alignItems="flex-start"
      borderRadius="lg"
      p={3}
      mb={noGutter ? 0 : 1}
      mt={0}
    >
      <Box width="100%" display="flex" flexDirection="column">
        <Box
          display="flex"
          justifyContent="space-between"
          alignItems={{ xs: "flex-start", sm: "center" }}
          flexDirection={{ xs: "column", sm: "row" }}
          mb={2}
        >
          <Typography variant="button" fontWeight="bold" textTransform="capitalize">
            {name}
          </Typography>

          <Box display="flex" alignItems="center" mt={{ xs: 2, sm: 0 }} ml={{ xs: -1.5, sm: 0 }}>
            <Box mr={1}>
              <Button variant="text" color="error">
                <Delete />
                <strong>Delete</strong>
              </Button>
            </Box>
            <Button variant="text">
              <Edit />
              <strong>Edit</strong>
            </Button>
          </Box>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Gender:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" textTransform="capitalize" color={"rgb(52, 71, 103)"}>
              {gender}
            </Typography>
          </Typography>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Date of Birth:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" textTransform="capitalize" color={"rgb(52, 71, 103)"}>
              {dob}
            </Typography>
          </Typography>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Email Address:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" color={"rgb(52, 71, 103)"}>
              {email}
            </Typography>
          </Typography>
        </Box>
        <Typography variant="caption" color="rgb(123, 128, 154)">
          Phone Number:&nbsp;&nbsp;&nbsp;
          <Typography variant="caption" fontWeight="bold" color={"rgb(52, 71, 103)"}>
            {phone_no}
          </Typography>
        </Typography>
      </Box>
    </Box>
  );
}

export default PassengerDetails;