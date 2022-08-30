import React, { useEffect } from 'react';
import type { FC } from 'react';
import { Link } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import type { PackageCategory } from '@app/types/PackageCategory';
import PackageCategoryService from '@app/service/PackageCategoryService';
import { useAppSelector } from '@app/store/RootStore';
import { useLocation, useNavigate } from 'react-router-dom';

const Categories: FC = () => {
  const { languages } = useAppSelector((state) => state);
  const location = useLocation();
  const navigate = useNavigate();
  const { isLoading, data, error, refetch } = useQuery<PackageCategory[]>(
    ['categories'],
    () => {
      return PackageCategoryService.getAllCategoriesByLanguage(
        languages.activeLang,
      );
    },
  );

  useEffect(() => {
    refetch();
  }, [languages.activeLang]);

  if (isLoading || !data) {
    return null;
  }

  if (error) {
    return null;
  }

  if (location.pathname == '/') {
    //navigate to first category
    navigate(`${data[0].name}`);
  }
  return (
    <React.Fragment>
      {data.map((item, i) => {
        return (
          <Link
            key={`${item.id}-${item.name}-${i}`}
            color='black'
            href={item.name}
            underline='hover'
          >
            {item.descriptions[0].content}
          </Link>
        );
      })}
    </React.Fragment>
  );
};

export default Categories;
