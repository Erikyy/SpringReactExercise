import React from 'react';
import type { FC } from 'react';
import { Box, CircularProgress, Container } from '@mui/material';
import { useQuery } from '@tanstack/react-query';

const PackagePage: FC = () => {
  const { isLoading, data, error } = useQuery(['package'], () => {});

  if (isLoading) {
    return (
      <Box>
        <CircularProgress />
      </Box>
    );
  }
  return <Container></Container>;
};

export default PackagePage;
