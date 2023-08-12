import { ArchiveHeader } from '@components/archive/ArchiveHeader';
import { ArchiveNavbar } from '@components/archive/ArchiveNavbar';
import { ArchiveOptionDetails } from '@components/archive/ArchiveOptionDetails';
import { ArchiveReview } from '@components/archive/ArchiveReview';
import { ArchiveShortInfo } from '@components/archive/ArchiveShortInfo';
import { Flex } from '@components/common';

export const Archive = () => {
  return (
    <Flex
      direction="column"
      justify="flex-start"
      gap={18}
      backgroundColor="Sand"
    >
      <Flex direction="column" justify="flex-start">
        <ArchiveHeader />
        <ArchiveNavbar />
        <ArchiveReview />
        <ArchiveShortInfo />
      </Flex>
      <ArchiveOptionDetails />
    </Flex>
  );
};
