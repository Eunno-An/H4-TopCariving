import styled from '@emotion/styled';
import { OptionCard } from '@components/archive/detail/archiveOptionCard/OptionCard';
import { ArchiveDetailPageProps } from '@pages/Archive/detail';
import { useEffect, useRef } from 'react';
import { masonryLayout } from '@components/common';

export const ArchiveOptionDetails = ({
  optionDetail,
}: ArchiveDetailPageProps) => {
  const masonryRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    setTimeout(() => {
      masonryLayout({ element: masonryRef });
    }, 100);
  }, [optionDetail]);

  return (
    <CardContainer ref={masonryRef}>
      {[
        ...(optionDetail?.['상세 품목'] || []),
        ...(optionDetail?.['N performance'] || []),
        ...(optionDetail?.['H Genuine Accessories'] || []),
      ].map((optionInfo, idx) => (
        <div key={`optionDetail_${idx}`}>
          <OptionCard info={optionInfo} />
        </div>
      ))}
    </CardContainer>
  );
};

const CardContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-auto-rows: 15px;

  width: 1040px;
  gap: 20px;

  padding: 30px 0 60px 0;
`;
