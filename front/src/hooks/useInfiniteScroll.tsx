import { RefObject, SetStateAction } from 'react';

interface useInfiniteScrollProps {
  element: RefObject<HTMLElement> | null;
  pageNum: number;
  setPageNum: (value: SetStateAction<number>) => void;
}

export const useInfiniteScroll = ({
  element,
  pageNum,
  setPageNum,
}: useInfiniteScrollProps) => {
  const observer = new IntersectionObserver(
    (entries) => {
      const entry = entries[0];
      if (entry.isIntersecting) {
        setTimeout(() => setPageNum(pageNum + 1), 1000);
      }
    },
    { threshold: 0.5 },
  );
  if (element?.current) {
    observer.observe(element.current);
  }

  return () => {
    if (element?.current) {
      observer.unobserve(element.current);
    }
  };
};
