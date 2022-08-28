import React, { useEffect } from 'react';
import type { FC } from 'react';
import { Link, Stack } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import type { PackageCategory } from '@app/types/PackageCategory';
import PackageCategoryService from '@app/service/PackageCategoryService';
import { useAppSelector } from '@app/store/RootStore';

interface CategoryProps {
  onClick: (val: number) => void;
}

const Categories: FC = () => {
  const { languages, categories } = useAppSelector((state) => state);

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
