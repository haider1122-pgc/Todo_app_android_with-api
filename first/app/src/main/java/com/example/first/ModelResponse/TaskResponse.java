package com.example.first.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
        public class TaskResponse {

            @SerializedName("success")
            @Expose
            private Boolean success;
            @SerializedName("data")
            @Expose
            private Data data;

            /**
             * No args constructor for use in serialization
             */
            public TaskResponse() {
            }

            /**
             * @param data
             * @param success
             */
            public TaskResponse(Boolean success, Data data) {
                super();
                this.success = success;
                this.data = data;
            }

            public Boolean getSuccess() {
                return success;
            }

            public void setSuccess(Boolean success) {
                this.success = success;
            }

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }


            public class Data {

                @SerializedName("completed")
                @Expose
                private Boolean completed;
                @SerializedName("_id")
                @Expose
                private  String id;
                @SerializedName("description")
                @Expose
                private String description;
                @SerializedName("owner")
                @Expose
                private String owner;
                @SerializedName("createdAt")
                @Expose
                private String createdAt;
                @SerializedName("updatedAt")
                @Expose
                private String updatedAt;
                @SerializedName("__v")
                @Expose
                private Integer v;

                /**
                 * No args constructor for use in serialization
                 */
                public Data() {
                }

                /**
                 * @param owner
                 * @param createdAt
                 * @param v
                 * @param description
                 * @param completed
                 * @param id
                 * @param updatedAt
                 */
                public Data(Boolean completed, String id, String description, String owner, String createdAt, String updatedAt, Integer v) {
                    super();
                    this.completed = completed;
                    this.id = id;
                    this.description = description;
                    this.owner = owner;
                    this.createdAt = createdAt;
                    this.updatedAt = updatedAt;
                    this.v = v;
                }

                public Boolean getCompleted() {
                    return completed;
                }

                public void setCompleted(Boolean completed) {
                    this.completed = completed;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getOwner() {
                    return owner;
                }

                public void setOwner(String owner) {
                    this.owner = owner;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public Integer getV() {
                    return v;
                }

                public void setV(Integer v) {
                    this.v = v;
                }

            }
        }





