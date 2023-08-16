import { ArchiveReview, ArchiveShortInfo } from '@components/archive/detail';
import { ArchiveOptionDetails } from '@components/archive/detail/ArchiveOptionDetails';
import { Flex } from '@components/common';

export const ArchiveDetail = () => {
  return (
    <>
      <ArchiveReview />
      <ArchiveShortInfo />
      <ArchiveOptionDetails />
    </>
  );
};
