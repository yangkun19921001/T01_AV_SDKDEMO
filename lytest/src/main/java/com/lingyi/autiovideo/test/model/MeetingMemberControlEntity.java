package com.lingyi.autiovideo.test.model;

import java.util.List;

/**
 * <pre>
 *     author  : devyk on 2020-03-11 16:33
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MeetingMemberControlEntity
 * </pre>
 */
public class MeetingMemberControlEntity {
    /**
     * conferences : {"conference":{"rate":"8000","enter_sound":"true","dynamic":"true","members":{"member":[{"type":"recording_node","join_time":{"content":"1583915546","type":"UNIX_epoch"},"record_path":"/usr/local/telemedia/recordings/c8858142_2020_03_11_16_32_26.wav","output_volume":"0","input_volume":"0","caller_id_name":"70001004","flags":{"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"},"uuid":"d680a7b2_6372_11ea_abdb_073317fa74f1","volume_in":"0","caller_id_number":"70001004","auto_adjusted_input_volume":"0","energy":"300","id":"22","volume_out":"0","last_talking":"N/A"},{"output_volume":"0","input_volume":"0","join_time":"4","caller_id_name":"70001004","flags":{"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"},"uuid":"d680a7b2_6372_11ea_abdb_073317fa74f1","volume_in":"0","caller_id_number":"70001004","auto_adjusted_input_volume":"0","energy":"300","id":"22","volume_out":"0","last_talking":"N/A","type":"caller"}]},"recording":"true","exit_sound":"true","uuid":"d694eb3c_6372_11ea_abe6_073317fa74f1","name":"c8858142","answered":"true","run_time":"4","ghost_count":"0","enforce_min":"true","member_count":"1","running":"true"}}
     */

    private ConferencesBean conferences;

    public ConferencesBean getConferences() {
        return conferences;
    }

    public void setConferences(ConferencesBean conferences) {
        this.conferences = conferences;
    }

    public static class ConferencesBean {
        /**
         * conference : {"rate":"8000","enter_sound":"true","dynamic":"true","members":{"member":[{"type":"recording_node","join_time":{"content":"1583915546","type":"UNIX_epoch"},"record_path":"/usr/local/telemedia/recordings/c8858142_2020_03_11_16_32_26.wav","output_volume":"0","input_volume":"0","caller_id_name":"70001004","flags":{"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"},"uuid":"d680a7b2_6372_11ea_abdb_073317fa74f1","volume_in":"0","caller_id_number":"70001004","auto_adjusted_input_volume":"0","energy":"300","id":"22","volume_out":"0","last_talking":"N/A"},{"output_volume":"0","input_volume":"0","join_time":"4","caller_id_name":"70001004","flags":{"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"},"uuid":"d680a7b2_6372_11ea_abdb_073317fa74f1","volume_in":"0","caller_id_number":"70001004","auto_adjusted_input_volume":"0","energy":"300","id":"22","volume_out":"0","last_talking":"N/A","type":"caller"}]},"recording":"true","exit_sound":"true","uuid":"d694eb3c_6372_11ea_abe6_073317fa74f1","name":"c8858142","answered":"true","run_time":"4","ghost_count":"0","enforce_min":"true","member_count":"1","running":"true"}
         */

        private ConferenceBean conference;

        public ConferenceBean getConference() {
            return conference;
        }

        public void setConference(ConferenceBean conference) {
            this.conference = conference;
        }

        public static class ConferenceBean {
            /**
             * rate : 8000
             * enter_sound : true
             * dynamic : true
             * members : {"member":[{"type":"recording_node","join_time":{"content":"1583915546","type":"UNIX_epoch"},"record_path":"/usr/local/telemedia/recordings/c8858142_2020_03_11_16_32_26.wav"},{"output_volume":"0","input_volume":"0","join_time":"4","caller_id_name":"70001004","flags":{"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"},"uuid":"d680a7b2_6372_11ea_abdb_073317fa74f1","volume_in":"0","caller_id_number":"70001004","auto_adjusted_input_volume":"0","energy":"300","id":"22","volume_out":"0","last_talking":"N/A","type":"caller"}]}
             * recording : true
             * exit_sound : true
             * uuid : d694eb3c_6372_11ea_abe6_073317fa74f1
             * name : c8858142
             * answered : true
             * run_time : 4
             * ghost_count : 0
             * enforce_min : true
             * member_count : 1
             * running : true
             */

            private String rate;
            private String enter_sound;
            private String dynamic;
            private MembersBean members;
            private String recording;
            private String exit_sound;
            private String uuid;
            private String name;
            private String answered;
            private String run_time;
            private String ghost_count;
            private String enforce_min;
            private String member_count;
            private String running;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getEnter_sound() {
                return enter_sound;
            }

            public void setEnter_sound(String enter_sound) {
                this.enter_sound = enter_sound;
            }

            public String getDynamic() {
                return dynamic;
            }

            public void setDynamic(String dynamic) {
                this.dynamic = dynamic;
            }

            public MembersBean getMembers() {
                return members;
            }

            public void setMembers(MembersBean members) {
                this.members = members;
            }

            public String getRecording() {
                return recording;
            }

            public void setRecording(String recording) {
                this.recording = recording;
            }

            public String getExit_sound() {
                return exit_sound;
            }

            public void setExit_sound(String exit_sound) {
                this.exit_sound = exit_sound;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAnswered() {
                return answered;
            }

            public void setAnswered(String answered) {
                this.answered = answered;
            }

            public String getRun_time() {
                return run_time;
            }

            public void setRun_time(String run_time) {
                this.run_time = run_time;
            }

            public String getGhost_count() {
                return ghost_count;
            }

            public void setGhost_count(String ghost_count) {
                this.ghost_count = ghost_count;
            }

            public String getEnforce_min() {
                return enforce_min;
            }

            public void setEnforce_min(String enforce_min) {
                this.enforce_min = enforce_min;
            }

            public String getMember_count() {
                return member_count;
            }

            public void setMember_count(String member_count) {
                this.member_count = member_count;
            }

            public String getRunning() {
                return running;
            }

            public void setRunning(String running) {
                this.running = running;
            }

            public static class MembersBean {
                private List<MemberBean> member;

                public List<MemberBean> getMember() {
                    return member;
                }

                public void setMember(List<MemberBean> member) {
                    this.member = member;
                }

                public static class MemberBean {
                    /**
                     * type : recording_node
                     * join_time : {"content":"1583915546","type":"UNIX_epoch"}
                     * record_path : /usr/local/telemedia/recordings/c8858142_2020_03_11_16_32_26.wav
                     * output_volume : 0
                     * input_volume : 0
                     * caller_id_name : 70001004
                     * flags : {"mute_detect":"false","has_floor":"false","can_hear":"true","end_conference":"false","can_speak":"true","has_video":"false","talking":"false","video_bridge":"false","is_moderator":"false","is_ghost":"false"}
                     * uuid : d680a7b2_6372_11ea_abdb_073317fa74f1
                     * volume_in : 0
                     * caller_id_number : 70001004
                     * auto_adjusted_input_volume : 0
                     * energy : 300
                     * id : 22
                     * volume_out : 0
                     * last_talking : N/A
                     */

                    private String type;
                    private JoinTimeBean join_time;
                    private String record_path;
                    private String output_volume;
                    private String input_volume;
                    private String caller_id_name;
                    private FlagsBean flags;
                    private String uuid;
                    private String volume_in;
                    private String caller_id_number;
                    private String auto_adjusted_input_volume;
                    private String energy;
                    private String id;
                    private String volume_out;
                    private String last_talking;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

//                    public JoinTimeBean getJoin_time() {
//                        return join_time;
//                    }
//
//                    public void setJoin_time(JoinTimeBean join_time) {
//                        this.join_time = join_time;
//                    }

                    public String getRecord_path() {
                        return record_path;
                    }

                    public void setRecord_path(String record_path) {
                        this.record_path = record_path;
                    }

                    public String getOutput_volume() {
                        return output_volume;
                    }

                    public void setOutput_volume(String output_volume) {
                        this.output_volume = output_volume;
                    }

                    public String getInput_volume() {
                        return input_volume;
                    }

                    public void setInput_volume(String input_volume) {
                        this.input_volume = input_volume;
                    }

                    public String getCaller_id_name() {
                        return caller_id_name;
                    }

                    public void setCaller_id_name(String caller_id_name) {
                        this.caller_id_name = caller_id_name;
                    }

                    public FlagsBean getFlags() {
                        return flags;
                    }

                    public void setFlags(FlagsBean flags) {
                        this.flags = flags;
                    }

                    public String getUuid() {
                        return uuid;
                    }

                    public void setUuid(String uuid) {
                        this.uuid = uuid;
                    }

                    public String getVolume_in() {
                        return volume_in;
                    }

                    public void setVolume_in(String volume_in) {
                        this.volume_in = volume_in;
                    }

                    public String getCaller_id_number() {
                        return caller_id_number;
                    }

                    public void setCaller_id_number(String caller_id_number) {
                        this.caller_id_number = caller_id_number;
                    }

                    public String getAuto_adjusted_input_volume() {
                        return auto_adjusted_input_volume;
                    }

                    public void setAuto_adjusted_input_volume(String auto_adjusted_input_volume) {
                        this.auto_adjusted_input_volume = auto_adjusted_input_volume;
                    }

                    public String getEnergy() {
                        return energy;
                    }

                    public void setEnergy(String energy) {
                        this.energy = energy;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getVolume_out() {
                        return volume_out;
                    }

                    public void setVolume_out(String volume_out) {
                        this.volume_out = volume_out;
                    }

                    public String getLast_talking() {
                        return last_talking;
                    }

                    public void setLast_talking(String last_talking) {
                        this.last_talking = last_talking;
                    }

                    public static class JoinTimeBean {
                        /**
                         * content : 1583915546
                         * type : UNIX_epoch
                         */

                        private String content;
                        private String type;

                        public String getContent() {
                            return content;
                        }

                        public void setContent(String content) {
                            this.content = content;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }
                    }

                    public static class FlagsBean {
                        /**
                         * mute_detect : false
                         * has_floor : false
                         * can_hear : true
                         * end_conference : false
                         * can_speak : true
                         * has_video : false
                         * talking : false
                         * video_bridge : false
                         * is_moderator : false
                         * is_ghost : false
                         */

                        private String mute_detect;
                        private String has_floor;
                        private String can_hear;
                        private String end_conference;
                        private String can_speak;
                        private String has_video;
                        private String talking;
                        private String video_bridge;
                        private String is_moderator;
                        private String is_ghost;

                        public String getMute_detect() {
                            return mute_detect;
                        }

                        public void setMute_detect(String mute_detect) {
                            this.mute_detect = mute_detect;
                        }

                        public String getHas_floor() {
                            return has_floor;
                        }

                        public void setHas_floor(String has_floor) {
                            this.has_floor = has_floor;
                        }

                        public String getCan_hear() {
                            return can_hear;
                        }

                        public void setCan_hear(String can_hear) {
                            this.can_hear = can_hear;
                        }

                        public String getEnd_conference() {
                            return end_conference;
                        }

                        public void setEnd_conference(String end_conference) {
                            this.end_conference = end_conference;
                        }

                        public String getCan_speak() {
                            return can_speak;
                        }

                        public void setCan_speak(String can_speak) {
                            this.can_speak = can_speak;
                        }

                        public String getHas_video() {
                            return has_video;
                        }

                        public void setHas_video(String has_video) {
                            this.has_video = has_video;
                        }

                        public String getTalking() {
                            return talking;
                        }

                        public void setTalking(String talking) {
                            this.talking = talking;
                        }

                        public String getVideo_bridge() {
                            return video_bridge;
                        }

                        public void setVideo_bridge(String video_bridge) {
                            this.video_bridge = video_bridge;
                        }

                        public String getIs_moderator() {
                            return is_moderator;
                        }

                        public void setIs_moderator(String is_moderator) {
                            this.is_moderator = is_moderator;
                        }

                        public String getIs_ghost() {
                            return is_ghost;
                        }

                        public void setIs_ghost(String is_ghost) {
                            this.is_ghost = is_ghost;
                        }
                    }
                }
            }
        }
    }
}
