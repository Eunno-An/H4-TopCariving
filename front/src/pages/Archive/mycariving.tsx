import { ArchiveHeader, ArchiveNavbar } from '@components/archive';
import {
  MyCarList,
  createdMyCarInterface,
} from '@components/archive/myCariving/MyCarList';
import { Flex } from '@components/common';
import { useLoaderData } from 'react-router-dom';

export const MyCariving = () => {
  const createdMyCar = useLoaderData() as createdMyCarInterface[];
  return (
    <Flex direction="column" justify="flex-start">
      <ArchiveHeader pageTitle={'마이카이빙'} />
      <ArchiveNavbar pageTitle={'마이카이빙'} />
      <MyCarList createdMyCar={createdMyCar} />
    </Flex>
  );
};
