import React from 'react';
import Button from '@mui/material/Button';  

class Users extends React.Component {

    constructor() {
        super();
    }
    render() {

        if(this.props.modalOpen || this.props.thisUser == '' )
            return null;

        return (
            <div className="user_list">
                {
                    //this.props.users
                    this.props.userList.map((user) => {
                        console.log(user.userName);
                        return (
                            <div key={user.userName}>
                            <Button variant="text" value={user.userName} onClick={(e) => {console.log(e.target.value); this.props.handleSelectUser(e.target.value) }}>{user.userName}</Button> 
                            <br></br>
                            </div>
                        );
                    })
                }
            </div>
        );
    }
}

/*Users.propTypes = {
    users: React.PropTypes.array.isRequired
};*/

export default Users;