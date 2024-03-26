-- If trigger exists then delete it
DROP TRIGGER IF EXISTS update_thread_timestamp;

-- create new trigger for updated timestamp change of thread
CREATE TRIGGER update_thread_timestamp AFTER INSERT ON tbl_messages FOR EACH ROW BEGIN UPDATE tbl_threads SET updated_at = CURRENT_TIMESTAMP() WHERE tbl_threads.thread_id = NEW.thread_id; END;
