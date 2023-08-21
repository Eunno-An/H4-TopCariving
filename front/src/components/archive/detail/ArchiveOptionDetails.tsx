import styled from '@emotion/styled';
import { OptionCard } from '@components/archive/detail/archiveOptionCard/OptionCard';
import { ArchiveDetailPageProps } from '@pages/Archive/detail';

export const ArchiveOptionDetails = ({
  optionDetail,
}: ArchiveDetailPageProps) => {
  const optionArr = optionDetail && [
    ...(optionDetail['상세 품목'] || []),
    ...(optionDetail['N performance'] || []),
    ...(optionDetail['H Genuine Accessories'] || []),
  ];

  return (
    <CardContainer>
      {optionArr &&
        optionArr.map((optionInfo, idx) => (
          <OptionCard info={optionInfo} key={`optionDetail_${idx}`} />
        ))}
    </CardContainer>
  );
};

const CardContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  /* grid-auto-rows: 10px; */
  width: 1040px;
  height: 100vh;
  gap: 10px;

  padding: 30px 0 60px 0;
`;
