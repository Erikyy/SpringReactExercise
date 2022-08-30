import React, { useEffect, useState } from 'react';
import type { FC } from 'react';
import {
  Container,
  Grid,
  Icon,
  IconButton,
  Snackbar,
  Typography,
} from '@mui/material';
import { useMutation, useQuery } from '@tanstack/react-query';
import PackageService from '@app/service/PackageService';
import type { Package } from '@app/types/Package';
import PackageCard from '@app/components/PackageCard';
import { useAppSelector } from '@app/store/RootStore';
import OrderService from '@app/service/OrderService';
import { useTranslation } from 'react-i18next';
import { T_NEW_ORDER_ADDED } from '@app/constants';
import Loading from '@app/components/common/Loading';
import { useLocation } from 'react-router-dom';

const PackagesPage: FC = () => {
  const { languages, currencies } = useAppSelector((state) => state);

  const [notificationOpen, setNotificationOpen] = useState(false);
  const { t } = useTranslation();
  const location = useLocation();

  const { isLoading, data, error, refetch } = useQuery<Package[]>(
    ['packages'],
    () => {
      return PackageService.getPackages(
        location.pathname.replace('/', ''),
        languages.activeLang,
        currencies.activeCurrency,
      );
    },
  );

  const mutation = useMutation((id: number) => {
    return OrderService.addNewOrder(id);
  });

  useEffect(() => {
    refetch();
  }, [languages.activeLang, currencies.activeCurrency]);

  if (isLoading) {
    return <Loading />;
  }
  if (error) {
    return <Typography>Error has occurred</Typography>;
  }

  return (
    <Container>
      <Grid
        padding={3}
        boxShadow={'none'}
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 2, sm: 8, md: 12 }}
      >
        {data &&
          data.map((pack, i) => {
            return (
              <Grid item xs={2} sm={4} md={4} key={`${i}-${pack.id}`}>
                <PackageCard
                  data={pack}
                  onOrderMade={(id) => {
                    mutation.mutate(id, {
                      onSuccess() {
                        setNotificationOpen(true);
                      },
                    });
                  }}
                />
              </Grid>
            );
          })}
      </Grid>

      <Snackbar
        open={notificationOpen}
        onClose={() => setNotificationOpen(false)}
        message={t(T_NEW_ORDER_ADDED)}
        autoHideDuration={3000}
        action={
          <IconButton
            aria-label='close'
            size='small'
            onClick={() => {
              setNotificationOpen(false);
            }}
          >
            <Icon fontSize='small' />
          </IconButton>
        }
      />
    </Container>
  );
};

export default PackagesPage;
