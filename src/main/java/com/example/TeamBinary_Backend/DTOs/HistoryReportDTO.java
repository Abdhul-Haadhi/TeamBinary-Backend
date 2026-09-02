package com.example.TeamBinary_Backend.DTOs;

public class HistoryReportDTO {




        private Long id;
        private String originalFileName;
        private String searchedAt;
        private String status;

        public HistoryReportDTO() {
        }

        public HistoryReportDTO(
                Long id,
                String originalFileName,
                String searchedAt,
                String status
        ) {
            this.id = id;
            this.originalFileName = originalFileName;
            this.searchedAt = searchedAt;
            this.status = status;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOriginalFileName() {
            return originalFileName;
        }

        public void setOriginalFileName(String originalFileName) {
            this.originalFileName = originalFileName;
        }

        public String getSearchedAt() {
            return searchedAt;
        }

        public void setSearchedAt(String searchedAt) {
            this.searchedAt = searchedAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }



}
