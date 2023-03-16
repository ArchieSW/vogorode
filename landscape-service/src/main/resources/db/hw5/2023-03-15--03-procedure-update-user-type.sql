create or replace procedure update_user_type(p_batch_size integer, p_pause_time integer)
    language plpgsql
as $$
declare
    v_total_rows integer;
    v_processed_rows integer := 0;
begin
    select count(*) into v_total_rows from users;

    while v_processed_rows < v_total_rows loop
            raise notice '% - processing batch of % rows...', now(), p_batch_size;

            update users
            set user_type_id = 2
            where id in (
                select id
                from users
                order by created_at
                limit p_batch_size
                    offset v_processed_rows
            );

            v_processed_rows := v_processed_rows + p_batch_size;

            perform pg_sleep(p_pause_time);

            raise notice '% - committed % rows. migration paused for % sec.', now(), p_batch_size, p_pause_time;
        end loop;
end;
$$;