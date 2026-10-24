package com.tourmanagement.config;

import java.util.Map;

public class AppConfig {

    private String activeProfile;
    private Map<String, ProfileConfig> profiles;

    public String getActiveProfile() {
        return  activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public Map<String, ProfileConfig> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, ProfileConfig> profiles) {
        this.profiles = profiles;
    }

    public static class ProfileConfig {
        private String daoType;
        private String url;
        private String username;
        private String password;
        private int poolSize;

        public String getDaoType() {
            return daoType;
        }

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public void setDaoType(String daoType) {
            this.daoType = daoType;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPoolSize(int poolSize) {
            this.poolSize = poolSize;
        }
    }
}
