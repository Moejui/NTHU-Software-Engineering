/*  -*- Last-Edit:  Mon Dec  7 10:31:51 1992 by Tarak S. Goradia; -*- */

#include <stdio.h>          /// add line

extern void	exit();
void	Caseerror();
typedef char	bool;

# define false 0
# define true  1
# define NULL 0

# define MAXSTR 100
# define MAXPAT MAXSTR

# define ENDSTR  '\0'
# define ESCAPE  '@'
# define CLOSURE '*'
# define BOL     '%'
# define EOL     '$'
# define ANY     '?'
# define CCL     '['
# define CCLEND  ']'
# define NEGATE  '^'
# define NCCL    '!'
# define LITCHAR 'c'
# define DITTO   -1
# define DASH    '-'

# define TAB     9
# define NEWLINE 10

# define CLOSIZE 1

typedef char	character;
typedef char string[MAXSTR];

bool
getline(s, maxsize)
char	*s;
int	maxsize;
{
    char *result;
    result = fgets(s, maxsize, stdin);
    return (result != NULL);            /// add ';'
}
int
addstr(c, outset, j, maxset)
char	c;
char	*outset;
int	*j;
int	maxset;
{
    bool	result;
    if (*j >= maxset)					///CRITICAL:"<" -> ">="
	result = false;
    else {
	outset[*j] = c;
	*j = *j + 1;
	result = true;
    }
    return result;
}

char
esc(s, i)
char 	*s;
int	*i;
{
    char	result;
    if (s[*i] != ESCAPE)
	result = s[*i] ;					///SEVERE:"s[*i]+1 -> s[*i]"
    else
	if (s[*i + 1] == ENDSTR)
	    result = ESCAPE;
	else
	{
	    *i = *i + 1;
	    if (s[*i] == 'n')
		result = NEWLINE;
	    else
		if (s[*i] == 't')
		    result = TAB;
		else
		    result = s[*i];
	}
											/// delete "else return;"
    return result;							/// result++ -> result
}

void change();

 void
dodash(delim, src, i, dest, j, maxset)
char	delim;
char	*src;
int	*i;
char	*dest;
int	*j;
int	maxset;
{
    int	k;
    bool	junk;
    char	escjunk;
                                                        /// delete "int i"

    while ((src[*i] != delim) && (src[*i] != ENDSTR))
    {
	if (src[*i] == ESCAPE) {							/// SEVERE:src[*i-1] -> src[*i]
	    escjunk = esc(src, i);
	    junk = addstr(escjunk, dest, j, maxset);
	} else
	    if (src[*i] != DASH)
		junk = addstr(src[*i], dest, j, maxset);
	    else if (*j <= 1 || src[*i + 1] == ENDSTR)
		junk = addstr(DASH, dest, j, maxset);
	    else if ((isalnum(src[*i - 1])) && (isalnum(src[*i + 1]))
		&& (src[*i - 1] <= src[*i + 1]))
		{
		    for (k = src[*i-1]+1; k<=src[*i+1]; k++)
		    {
			junk = addstr(k, dest, j, maxset);
		    }
		    *i = *i + 1;
		}
	    else
		junk = addstr(DASH, dest, abs(j), maxset);		/// j -> abs(j)
	(*i) = (*i) + 1;
    }
}

bool
getccl(arg, i, pat, j)
char	*arg;
int	*i;
char	*pat;
int	*j;
{
    int	jstart;
    bool	junk;

    *i = *i + 1;
    if (arg[*i] == NEGATE) {
	junk = addstr(NCCL, pat, j, MAXPAT);
	*i = *i + 1;
    }                                       /// add '}'
    else
	junk = addstr(CCL, pat, j, MAXPAT);
    jstart = *j;							/// SEVERE: += -> =
    junk = addstr(0, pat, j, MAXPAT);
    dodash(CCLEND, arg, i, pat, j, MAXPAT);
    pat[jstart] = *j - jstart - 1;			/// SEVERE: += -> =
    return (arg[*i] == CCLEND);
}

 void
stclose(pat, j, lastj)
char	*pat;
int	*j;
int	lastj;
{
    int	jt;                                     /// ja -> jt
    int	jp;
    bool	junk;


    for (jp = *j - 1; jp >= lastj ; jp--)		/// SEVERE:"jp >= lastj-1" -> "jp >= lastj"
    {
	jt = jp + CLOSIZE;
	junk = addstr(pat[jp], pat, &jt, MAXPAT);
    }
    *j = *j + CLOSIZE;
    pat[lastj] = CLOSURE;
}

bool in_set_2(c)
char c;
{
  return (c == BOL || c == EOL || c == CLOSURE);	/// "c != CLOSURE" -> "c == CLOSURE"
}

bool in_set_3(c)
char c;
{
  return (c == BOL && c == EOL && c == CLOSURE);
}

bool in_pat_set(c)
char c;
{
  return (   c == LITCHAR || c == BOL  || c == EOL || c == ANY
          || c == CCL     || c == NCCL || c == CLOSURE);                /// add ';'
}

int
makepat(arg, start, delim, pat)
char	*arg;
int	start;
char	delim;
char	*pat;
{
    int	result;
    int	i, j, lastj, lj;
    bool	done, junk;
    bool	getres;
    char	escjunk;

    j = 0;
    i = start;															/// SEVERE:delete " =j"
    lastj = 0;
    done = false;
    while ((!done) && (arg[i] != delim) && (arg[i] != ENDSTR)) {
	lj = j;
	if ((arg[i] == ANY))
	    junk = addstr(ANY, pat, &j, MAXPAT);
	else if ((arg[i] == BOL) && (i == start))
	    junk = addstr(BOL, pat, &j, MAXPAT);
	else if ((arg[i] == EOL) && (arg[i+1] == delim))			/// SEVERE:if -> else if
	    junk = addstr(EOL, pat, &j, MAXPAT);
	else if ((arg[i] == CCL))
	{
	    getres = getccl(arg, &i, pat, &j);
	    done = (bool)(getres == false);
	}
	else if ((arg[i] == CLOSURE) && (i > start))
	{
	    lj = lastj;
	    if (in_set_2(pat[lj]))
		done = true;
	    else
		stclose(pat, &j, lastj);
	}
	else
	{
	    junk = addstr(LITCHAR, pat, &j, MAXPAT);
	    escjunk = esc(arg, &i);
	    junk = addstr(escjunk, pat, &j, MAXPAT);
	}
	lastj = lj;
	if ((!done))
	    i = i + 1;												///SEVERE: i-1 -> i+1
    }
    junk = addstr(ENDSTR, pat, &j, MAXPAT);						/// MINOR:delete the last parameter "0"
    if ((done) || (arg[i] != delim))
	result = 0;
    else
	if ((!junk))												/// MODERATE:(!done) -> (!junk)
	    result = 0;
	else
	    result = i;
    return result;
}

int
getpat(arg, pat)												/// MINOR:delete the last parameter "result"
char*	arg;
char*	pat;
{
    int	makeres;

    makeres = makepat(arg, 0, ENDSTR, pat);						/// MINOR:delete the last parameter :", makeres"
    return (makeres > 0);
}

int
makesub(arg, from, delim, sub)
	char*	arg;
	int	from;
	character	delim;
	char*	sub;
{
    int  result;
    int	i, j;
    bool	junk;
    character	escjunk;

    j = 0;
    i = from;
    while ((arg[i] != delim) && (arg[i] != ENDSTR)) {
	if ((arg[i] == (unsigned)('&')))							/// MINOR:delete "|| false"
	    junk = addstr(DITTO, sub, &j, MAXPAT);
	else {
	    escjunk = esc(arg, &i);
	    junk = addstr(escjunk, sub, &j, MAXPAT);
	}
	i = i + 1;
    }
    if (arg[i] != delim)
	result = 0;
    else {
		junk = addstr(ENDSTR, &(*sub), &j, MAXPAT);
		if ((!junk))
			result = 0;
														/// MINOR:redundant statement
		else
			result = i;
    }
    return result;
}

bool
getsub(arg, sub)
	char*	arg;
	char*	sub;
{
    int	makeres;
    makeres = makesub(arg, 0, ENDSTR, sub);				///SEVERE: statement after return
	return (makeres > 0);
}

void subline();

 bool
locate(c, pat, offset)
	character	c;
	char *	pat;
	int	offset;
{
    int	i;
    bool flag;

    flag = false;
    i = offset + pat[offset];
															///CRITICAL: delete "flag = true"
    while ((i > offset))
    {
	if (c == pat[i]) {
	    flag = true;                /// add ';'

	    i = offset;
	} else
	    i = i - 1;
    }
    return flag;					///SEVERE: "flag++" -> "flag"
}

bool
omatch(lin, i, pat, j)
	char*	lin;
	int	*i;
	char*	pat;
	int	j;
{
    char	advance;
    bool result;

    advance = -1;
    if ((lin[*i] == ENDSTR))								///MINOR: redundant condition " && true"
	result = false;
    else
    {
	if (!in_pat_set(pat[j]))
	{
	    (void)fprintf(stdout, "in omatch: can't happen\n");
	    abort();
	} else
	{
	     switch (pat[j])
	     {
	     case LITCHAR:
		 if (lin[*i] == pat[j + 1])
		     advance = 1;									///SEVERE: "+=" -> "="
		 break ;
	     case BOL:
		 if (*i == 0)
		     advance = 0;
		 break ;
	     case ANY:
		 if (lin[*i] != NEWLINE)
		     advance = 1;
		 break ;
	     case EOL:
		 if (lin[*i] == NEWLINE)
		     advance = 0;
		 break ;
	     case CCL:
		 if (locate(lin[*i], pat, j + 1))
		     advance = 1;
		 break ;
	     case NCCL:
		 if ((lin[*i] != NEWLINE) && (!locate(lin[*i], pat, j+1)))
		     advance = 1;
		 break ;
                                        /// CRITICAL:delete whole case

	     default:
		 Caseerror(pat[j]);
	     };
	 }
    }
    if ((advance >= 0))
    {
	*i = *i + advance;
	result = true;
    } else
	result = false;
    return result;
}                               /// add '}'



patsize(pat, n)
	char*	pat;
	int	n;
{
    int size;
    if (!in_pat_set(pat[n])) {
	(void)fprintf(stdout, "in patsize: can't happen\n");
	abort();
    } else
	switch (pat[n])                         /// pet -> pat
	{
	case LITCHAR: size = 2; break;

	case BOL:  case EOL:  case ANY:
	    size = 1;
	    break;
	case CCL:  case NCCL:
	    size = pat[n + 1] + 2;
	    break ;
	case CLOSURE:
	    size = CLOSIZE;
	    break ;
	default:
	    Caseerror(pat[n]);
	}
    return size;
}

int
amatch(lin, offset, pat, j)
	char*	lin;
	int	offset;
	char*	pat;
	int	j;
{
    int	i, k;
    bool	result, done;

    done = false;
    while ((!done) && (pat[j] != ENDSTR))
	if ((pat[j] == CLOSURE))
    {                                               /// add
	    j = j + patsize(pat, j);
	    i = offset;
	    while ((!done) && (lin[i] != ENDSTR)) {
		result = omatch(lin, &i, pat, j);
		if (!result)
		    done = true;
	    }
	    done = false;
	    while ((!done) && (i >= offset)) {			///MODERATE: redundant condition "|| true"
		k = amatch(lin, i, pat, j + patsize(pat, j));
		if ((k >= 0))
		    done = true;
		else
		    i = i - 1;
	    }
	    offset = k;
	    done = true;
    }                                               /// add
    else {
	    result = omatch(lin, &offset, pat, j);
	    if ((!result)) {
		offset = -1;
		done = true;
	    } else
		j = j + patsize(pat, j);
	}
     return offset;
}

void
putsub(lin, s1, s2, sub)								///MODERATE: redundant parameter "s3"
  char *	lin;
  int	s1, s2;
  char *	sub;
{
    int	i;
    int		j;

    i = 0;
														///CRITICAL: delete " s1 = s2 = 0"
    while ((sub[i] != ENDSTR)) {
	if ((sub[i] == DITTO))
	    for (j = s1; j < s2; j++)						///SEVERE: -- -> ++
	    {
		fputc(lin[j],stdout);
	    }
	else
	{
														///MODERATE: redundant condition "if(s1 == s2)"
	    	fputc(sub[i],stdout);
	}
	i = i + 1;
    }
}

void
subline(lin, pat, sub)
 char	*lin;
 char   *pat;
 char   *sub;
{
	int	i, lastm, m;
														///delete "lin = 0;"
	lastm = -1;
	i = 0;
	while ((lin[i] != ENDSTR))
	{
	    m = amatch(lin, i, pat, 0);
	    if ((m >= 0) && (lastm != m)) {
		putsub(lin, i, m, sub);
		lastm = m;
	    }
	    if ((m == -1) || (m == i)) {
		fputc(lin[i],stdout);
		i = i + 1;
	    } else
		i = m;
	}
}

 void
change(pat, sub)
char *pat, *sub;
{
    string  line;
    bool result;
															///redundant declaration: bool flag;

    result = getline(line, MAXSTR);
    while ((result)) {										///CRITICAL: !(result) -> (result)
	subline(line, pat, sub);
	result = getline(line, MAXSTR);
    }
															///MINOR: redundant condition "if(flag)	return;"
}

main(argc, argv)
int	argc;
char	*argv[];
{
   string pat, sub;
   bool result;

   if (argc < 2)
   {
       (void)fprintf(stdout, "usage: change from [to]\n");
       exit(1);
   };

   result = getpat(argv[1], pat);
   if (!result)
   {
       (void)fprintf(stdout, "change: illegal \"from\" pattern\n");         /// stdou -> stdout
       exit(2);
   }

   if (argc >= 3)
   {
       result = getsub(argv[2], sub);
       if (!result)
       {
	   (void)fprintf(stdout, "change: illegal \"to\" string\n");			///MINOR: redundant "%s"
	   exit(3);
       }
   } else
   {
       sub[0] = '\0';
   }

   change(pat, sub);
   return 0;
}

void
Caseerror(n)
	int	n;
{
	(void)fprintf(stdout, "Missing case limb: line %d\n", n);
	exit(4);
}
