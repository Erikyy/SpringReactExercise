import React from 'react';
import type { FC } from 'react';
import {
  Box,
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  Typography,
} from '@mui/material';
import type { Package } from '@app/types/Package';
import { useTranslation } from 'react-i18next';
import { useAppSelector } from '@app/store/RootStore';
import { T_MAKE_ORDER } from '@app/constants';
import getSymbolFromCurrency from 'currency-symbol-map';

interface PackageCardProps {
  data: Package;
  onOrderMade: (id: number) => void;
}

const PackageCard: FC<PackageCardProps> = ({ data, onOrderMade }) => {
  const activeCurrency = useAppSelector(
    (state) => state.currencies.activeCurrency,
  );

  const { t } = useTranslation();
  return (
    <Card
      sx={{
        minHeight: 300,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
        paddingBottom: 10,
      }}
    >
      <CardActionArea>
        <CardContent>
          <Typography align='center' fontSize={30}>
            {data.packageType.toString().charAt(0) +
              data.packageType.toString().substring(1).toLowerCase()}
          </Typography>
          <Typography align='center'>{data.descriptions[0].content}</Typography>
          <Typography align='center'>
            {data.price} {getSymbolFromCurrency(activeCurrency)}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions
        sx={{
          display: 'flex',
          justifyContent: 'center',
        }}
      >
        <Button
          onClick={(e) => {
            e.preventDefault();
            onOrderMade(data.id);
          }}
          variant='contained'
        >
          {t(T_MAKE_ORDER)}
        </Button>
      </CardActions>
    </Card>
  );
};

export default PackageCard;
