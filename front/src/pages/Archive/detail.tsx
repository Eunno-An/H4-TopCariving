import { ArchiveReview, ArchiveShortInfo } from '@components/archive/detail';
import { ArchiveOptionDetails } from '@components/archive/detail/ArchiveOptionDetails';
import { Flex } from '@components/common';

export const ArchiveDetail = () => {
  return (
    <>
      <ArchiveReview />
      <ArchiveShortInfo />
      <Flex height={18} backgroundColor="LightSand"></Flex>
      <ArchiveOptionDetails />
    </>
  );
};
