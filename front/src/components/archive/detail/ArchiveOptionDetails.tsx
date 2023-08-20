import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { OptionCard } from '@components/archive/detail/archiveOptionCard/OptionCard';
import { ArchiveDetailPageProps } from '@pages/Archive/detail';

export const ArchiveOptionDetails = ({
  optionDetail,
}: ArchiveDetailPageProps) => {
  return (
    <Flex
      width={1044}
      justify="space-between"
      padding="40px 0 0 0"
      gap={24}
      backgroundColor="White"
    >
      {[0, 1, 2].map((number, idx) => (
        <CardContainer key={idx}>
          {optionDetail?.['상세 품목'].map(
            (optionInfo, idx) =>
              idx % 3 == number && <OptionCard info={optionInfo} />,
          )}
        </CardContainer>
      ))}
    </Flex>
  );
};

const CardContainer = styled(Flex)`
  flex-direction: column;
  width: 331px;
  gap: 24px;
  justify-content: flex-start;
`;
