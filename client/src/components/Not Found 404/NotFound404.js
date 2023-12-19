import React from 'react'
import { Grid, Typography } from '@mui/material'

function NotFound404() {
  return (
    <Grid container justifyContent="center" alignItems="center">
      <Grid item m={'10%'}>
        <Typography variant="h1" color="primary" align="center">
          404
        </Typography>
        <Typography variant="h4" color="primary" align="center">
          Page Not Found
        </Typography>
      </Grid>
    </Grid>
  )
}

export default NotFound404