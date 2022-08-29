import React from 'react';
import type { FC } from 'react';
import { Box, CircularProgress } from '@mui/material';

const Loading: FC = () => {
  return (
    <Box
      display='flex'
      justifyContent='center'
      alignItems='center'
      minHeight='100vh'
    >
      <CircularProgress />
    </Box>
  );
};

export default Loading;
